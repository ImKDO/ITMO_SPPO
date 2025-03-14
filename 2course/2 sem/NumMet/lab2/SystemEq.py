from math import *

# Исправленный класс SystemEq
class SystemEq:
    def __init__(self, regex: [str, str]):
        """Инициализация системы из двух уравнений."""
        self.regex = regex  # Список из двух строк, представляющих уравнения
        self.funcs = None
        self.eq_replace_sym()

    def eq_replace_sym(self):
        """Замена символов ^ на ** и создание функций для вычисления."""
        try:
            print(f"Исходные уравнения: {self.regex}")  # Отладочный вывод
            self.regex = [eq.replace("^", "**") for eq in self.regex]
            print(f"Уравнения после замены ^ на **: {self.regex}")  # Отладочный вывод

            # Создаём лямбда-функции с явным указанием аргументов x и y
            self.funcs = [
                eval(f"lambda x, y: {self.regex[0]}",
                     {"sin": sin, "cos": cos, "e": exp(1), "log": log}),
                eval(f"lambda x, y: {self.regex[1]}",
                     {"sin": sin, "cos": cos, "e": exp(1), "log": log})
            ]
        except Exception as e:
            raise ValueError(f"Ошибка при преобразовании уравнений: {str(e)}")

    def safe_eval(self, x, y, eq_index):
        """Безопасное вычисление функции с заданным индексом."""
        print(f"Вычисляем safe_eval с x={x}, y={y}, eq_index={eq_index}")  # Отладочный вывод
        if self.funcs is None or eq_index not in [0, 1]:
            raise ValueError("Функции не инициализированы или неверный индекс")
        try:
            result = self.funcs[eq_index](x, y)
            if abs(result) > 1e308:
                raise OverflowError("Результат слишком велик")
            return result
        except Exception as e:
            print(31313)
            raise ValueError(f"Ошибка вычисления: {str(e)}")

    def jacobian(self, x, y):
        """Численное приближение Якобиана системы (матрица 2x2)."""
        h = 1e-7  # Маленький шаг для численного дифференцирования
        J = [[0.0, 0.0], [0.0, 0.0]]  # Матрица Якобиана 2x2
        J[0][0] = (self.safe_eval(x + h, y, 0) - self.safe_eval(x - h, y, 0)) / (2 * h)  # df1/dx
        J[0][1] = (self.safe_eval(x, y + h, 0) - self.safe_eval(x, y - h, 0)) / (2 * h)  # df1/dy
        J[1][0] = (self.safe_eval(x + h, y, 1) - self.safe_eval(x - h, y, 1)) / (2 * h)  # df2/dx
        J[1][1] = (self.safe_eval(x, y + h, 1) - self.safe_eval(x, y - h, 1)) / (2 * h)  # df2/dy
        print(f"Якобиан: {J}")  # Отладочный вывод
        return J

    def solve_linear_system(self, J, F):
        """Решение системы линейных уравнений J * delta = F для матрицы 2x2."""
        a, b = J[0][0], J[0][1]
        c, d = J[1][0], J[1][1]
        f1, f2 = F[0], F[1]

        # Вычисляем определитель
        det = a * d - b * c
        if abs(det) < 1e-10:
            raise ValueError("Хозяина, Якобиан сингулярен, метод не может продолжаться!")

        # Решаем систему методом Крамера
        delta_x = (d * f1 - b * f2) / det
        delta_y = (-c * f1 + a * f2) / det
        return [delta_x, delta_y]

    def newton_system(self, x0, y0, tol, max_iter=100):
        """Метод Ньютона для системы нелинейных уравнений."""
        x = x0
        y = y0
        for k in range(max_iter):
            # Вычисляем значения функций в текущей точке
            f1 = self.safe_eval(x, y, 0)
            f2 = self.safe_eval(x, y, 1)
            F = [-f1, -f2]  # Вектор правой части

            # Вычисляем Якобиан
            J = self.jacobian(x, y)

            # Решаем систему J * delta = F
            delta = self.solve_linear_system(J, F)

            # Обновляем приближение
            x_new = x + delta[0]
            y_new = y + delta[1]

            # Проверяем сходимость
            norm = (delta[0] ** 2 + delta[1] ** 2) ** 0.5
            if norm < tol:
                return x_new, y_new

            x, y = x_new, y_new

            # Проверяем максимальное количество итераций
            if k == max_iter - 1:
                raise ValueError("Хозяина, метод Ньютона не сошёлся за заданное число итераций!")

        raise ValueError("Хозяина, метод Ньютона не сошёлся!")