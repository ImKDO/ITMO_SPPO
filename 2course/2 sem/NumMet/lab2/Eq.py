from math import *
import numpy as np

class Eq:
    def __init__(self, regex: str):
        self.regex = regex
        self.func = None
        self.eq_replace_sym()

    def eq_replace_sym(self):
        self.regex = self.regex.replace("^", "**")
        try:
            self.func = eval(f"lambda x: {self.regex}", {"sin": sin, "cos": cos, "e": exp(1), "exp": exp, "log": log, "__builtins__": {}})
        except Exception as e:
            raise ValueError(f"Ошибка при преобразовании уравнения: {str(e)}")

    def safe_eval(self, x):
        if self.func is None:
            raise ValueError("Функция не инициализирована")
        try:
            result = self.func(x)
            if abs(result) > 1e308:
                raise OverflowError("Результат слишком велик")
            return result
        except Exception as e:
            raise ValueError(f"Ошибка вычисления: {str(e)}")

    def hord(self, a, b, tol, track_iter=False):
        iterations = 0
        while abs(b - a) > tol:
            try:
                fa = self.safe_eval(a)
                fb = self.safe_eval(b)
                if abs(fb - fa) < 1e-12:
                    return (a + b) / 2, iterations if track_iter else (a + b) / 2
                c = a - fa * (b - a) / (fb - fa)
                fc = self.safe_eval(c)
                iterations += 1
                if abs(fc) < tol:
                    return c, iterations if track_iter else c
                elif fa * fc < 0:
                    b = c
                else:
                    a = c
            except OverflowError:
                raise OverflowError("Переполнение при вычислении в методе хорд")
        return (a + b) / 2, iterations if track_iter else (a + b) / 2

    def secant(self, x0, x1, tol, max_iter=100, track_iter=False):
        iterations = 0
        for _ in range(max_iter):
            try:
                f_x0 = self.safe_eval(x0)
                f_x1 = self.safe_eval(x1)
                if abs(f_x1 - f_x0) < 1e-12:
                    return x1, iterations if track_iter else x1
                x2 = x1 - f_x1 * (x1 - x0) / (f_x1 - f_x0)
                iterations += 1
                if abs(x2 - x1) < tol:
                    return x2, iterations if track_iter else x2
                x0, x1 = x1, x2
            except OverflowError:
                raise OverflowError("Переполнение при вычислении в методе секущих")
        raise ValueError("Метод секущих не сошелся")

    def simple_iteration(self, x0, tol, x_min, x_max, k, max_iter=100, track_iter=False):
        """
        Метод простой итерации с учётом интервала.
        x0: начальное приближение
        tol: погрешность
        x_min, x_max: границы интервала
        k: коэффициент для phi(x) = x - k * f(x)
        max_iter: максимальное число итераций
        track_iter: возвращать ли количество итераций
        """
        # Проверяем, что x0 в интервале
        if not (x_min <= x0 <= x_max):
            raise ValueError(f"Начальное приближение x0={x0} вне интервала [{x_min}, {x_max}]")

        # Определяем итерационную функцию phi(x) = x - k * f(x)
        phi = lambda x: x - k * self.safe_eval(x)

        # Запускаем итерации
        iterations = 0
        x_prev = x0
        for _ in range(max_iter):
            try:
                x_next = phi(x_prev)
                iterations += 1

                # Корректируем x_next, чтобы он оставался в интервале
                x_next = max(x_min, min(x_max, x_next))

                # Проверяем значение функции в точке x_next
                f_value = self.safe_eval(x_next)
                if f_value is None:
                    raise ValueError(f"Не удалось вычислить f(x) в точке x={x_next}")

                # Новый критерий остановки: |f(x)| < tol
                if abs(f_value) < tol:
                    return x_next, iterations if track_iter else x_next

                # Дополнительный критерий: если x перестаёт меняться значительно
                if abs(x_next - x_prev) < tol * 1e-3:  # Добавляем более жёсткий критерий на изменение x
                    return x_next, iterations if track_iter else x_next

                x_prev = x_next
            except OverflowError:
                raise OverflowError("Переполнение при вычислении в методе итераций")

        raise ValueError("Метод простых итераций не сошелся за указанное число итераций")