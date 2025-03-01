from math import *
class Eq:
  def __init__(self, regex: str):
    self.regex = regex
  def eq_replace_sym(self):
    self.regex = self.regex.replace("^", "**")
    self.regex = self.regex.replace("sin", "sin")
    self.regex = self.regex.replace("e", "e")
  def hord(self, a, b, tol):
    self.eq_replace_sym()
    print(self.regex)
    func = eval(f"lambda x: {self.regex}")
    while abs(b - a) > tol:
        c = a - func(a) * (b - a) / (func(b) - func(a))
        if func(c) == 0:
            return c
        elif func(a) * func(c) < 0:
            b = c
        else:
            a = c
    return (a + b) / 2
  def secant(self, x0, x1, tol, max_iter=100):
        """Метод секущих"""
        self.eq_replace_sym()
        func = eval(f"lambda x: {self.regex}")

        for _ in range(max_iter):
            f_x0, f_x1 = func(x0), func(x1)
            if abs(f_x1 - f_x0) < 1e-12:  # Защита от деления на 0
                return x1

            x2 = x1 - f_x1 * (x1 - x0) / (f_x1 - f_x0)  # Формула метода секущих

            if abs(x2 - x1) < tol:
                return x2

            x0, x1 = x1, x2  # Обновляем точки

        raise ValueError("Метод секущих не сошелся за указанное число итераций")
  def simple_iteration(self, x0, tol, max_iter=100):
        """Метод простых итераций"""
        self.eq_replace_sym()
        phi = eval(f"lambda x: {self.regex}")  # Функция φ(x)

        for _ in range(max_iter):
            x1 = phi(x0)  # x_{n+1} = φ(x_n)
            if abs(x1 - x0) < tol:  # Проверка сходимости
                return x1
            x0 = x1  # Переход к следующей итерации

        raise ValueError("Метод простых итераций не сошелся за указанное число итераций")
  # def newton(self, e):
  #   # ...