from math import *
import tkinter as tk
from tkinter import ttk, filedialog
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import os
import numpy as np

from Eq import Eq
from SystemEq import SystemEq


class EquationSolverApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Решатель уравнений")
        self.root.geometry("800x600")

        self.equation_types = {
            "Система НУ": [
                "x^2 + y^2 - 4, sin(x) + cos(y) - 1",
                "x^3 - 3.125*x^2 - 3.5*x + y + 2.458, sin(e^x)*x^3 + x - y + 1"
            ],
            "НУ": ["x^3 - 3.125*x^2 - 3.5*x + 2.458",
                   "sin(e^x)*x^3 + x + 1",
                   "0.000123*x^2 + x + 1",
                   "e^x*x^3 + 0.00001*x^2 + x + 0.125",
                   "x^2-2*x+0.5"
                   ]
        }

        self.methods = {
            "Система НУ": ["Метод Ньютона"],
            "НУ": ["Метод простой итерации", "Метод секущих", "Метод хорд"]
        }

        self.current_eq = None
        self.iter_count = 0
        self.create_widgets()

    def create_widgets(self):
        selection_frame = ttk.Frame(self.root)
        selection_frame.pack(pady=10, padx=10, fill="x")

        selection_frame.columnconfigure(0, weight=1)
        selection_frame.columnconfigure(1, weight=1)

        ttk.Label(selection_frame, text="Что хотеть мая хозяина?", justify='center', width=30).grid(row=0, column=0,
                                                                                                    columnspan=2,
                                                                                                    padx=5, pady=5,
                                                                                                    sticky='nsew')

        ttk.Label(selection_frame, text="Тип уравнения:").grid(row=1, column=0, padx=5, pady=2, sticky='e')
        self.type_var = tk.StringVar()
        self.type_combo = ttk.Combobox(selection_frame, textvariable=self.type_var,
                                       values=list(self.equation_types.keys()))
        self.type_combo.grid(row=1, column=1, padx=5, pady=2, sticky='ew')
        self.type_combo.bind("<<ComboboxSelected>>", self.update_equations)

        ttk.Label(selection_frame, text="Уравнение:").grid(row=2, column=0, padx=5, pady=2, sticky='e')
        self.eq_var = tk.StringVar()
        self.eq_combo = ttk.Combobox(selection_frame, textvariable=self.eq_var)
        self.eq_combo.grid(row=2, column=1, padx=5, pady=2, sticky='ew')
        self.eq_combo.bind("<<ComboboxSelected>>", self.update_graph)

        ttk.Label(selection_frame, text="Метод решения:").grid(row=3, column=0, padx=5, pady=2, sticky='e')
        self.method_var = tk.StringVar()
        self.method_combo = ttk.Combobox(selection_frame, textvariable=self.method_var)
        self.method_combo.grid(row=3, column=1, padx=5, pady=2, sticky='ew')

        params_frame = ttk.Frame(self.root)
        params_frame.pack(pady=10, padx=10, fill="x")

        ttk.Label(params_frame, text="Интервал от:").grid(row=0, column=0, padx=5, pady=2, sticky='e')
        self.x_min = tk.DoubleVar(value=0.5)
        ttk.Entry(params_frame, textvariable=self.x_min, width=10).grid(row=0, column=1, padx=5, pady=2)

        ttk.Label(params_frame, text="до:").grid(row=0, column=2, padx=5, pady=2, sticky='e')
        self.x_max = tk.DoubleVar(value=2.0)
        ttk.Entry(params_frame, textvariable=self.x_max, width=10).grid(row=0, column=3, padx=5, pady=2)

        ttk.Label(params_frame, text="Погрешность:").grid(row=1, column=0, padx=5, pady=2, sticky='e')
        self.error = tk.DoubleVar(value=0.01)
        ttk.Entry(params_frame, textvariable=self.error, width=10).grid(row=1, column=1, padx=5, pady=2)

        self.x0_label = ttk.Label(params_frame, text="Начальное x0:")
        self.x0_var = tk.DoubleVar(value=-1.0)
        self.x0_entry = ttk.Entry(params_frame, textvariable=self.x0_var, width=10)
        self.y0_label = ttk.Label(params_frame, text="Начальное y0:")
        self.y0_var = tk.DoubleVar(value=-1.0)
        self.y0_entry = ttk.Entry(params_frame, textvariable=self.y0_var, width=10)

        self.save_to_file_var = tk.BooleanVar()
        self.save_check = ttk.Checkbutton(params_frame, text="Сохранить в файл", variable=self.save_to_file_var)
        self.save_check.grid(row=2, column=0, columnspan=2, padx=5, pady=5)

        self.load_button = ttk.Button(params_frame, text="Загрузить из файла", command=self.load_from_file)
        self.load_button.grid(row=2, column=2, columnspan=2, padx=5, pady=5)

        self.refresh_button = ttk.Button(params_frame, text="Обновить график", command=self.refresh_graph)
        self.refresh_button.grid(row=3, column=0, columnspan=4, padx=5, pady=5)

        ttk.Button(self.root, text="Решить", command=self.solve).pack(pady=10)

        self.result_frame = ttk.Frame(self.root)
        self.result_frame.pack(pady=10, padx=10, fill="both", expand=True)

        self.result_label = ttk.Label(self.result_frame, text="", wraplength=600)
        self.result_label.pack(fill="x", expand=True)

        self.canvas_frame = ttk.Frame(self.result_frame)
        self.canvas_frame.pack(fill="both", expand=True)

    def update_equations(self, event):
        eq_type = self.type_var.get()
        self.eq_combo['values'] = self.equation_types.get(eq_type, [])
        self.eq_combo.set("")
        self.update_methods(event)

        if eq_type == "Система НУ":
            self.save_check.grid(row=2, column=0, columnspan=2, padx=5, pady=5)
            self.load_button.grid(row=2, column=2, columnspan=2, padx=5, pady=5)
            self.refresh_button.grid(row=3, column=0, columnspan=4, padx=5, pady=5)
            self.x0_label.grid(row=4, column=0, padx=5, pady=2, sticky='e')
            self.x0_entry.grid(row=4, column=1, padx=5, pady=2)
            self.y0_label.grid(row=4, column=2, padx=5, pady=2, sticky='e')
            self.y0_entry.grid(row=4, column=3, padx=5, pady=2)
        else:
            self.save_check.grid(row=2, column=0, columnspan=2, padx=5, pady=5)
            self.load_button.grid(row=2, column=2, columnspan=2, padx=5, pady=5)
            self.refresh_button.grid(row=3, column=0, columnspan=4, padx=5, pady=5)
            self.x0_label.grid_remove()
            self.x0_entry.grid_remove()
            self.y0_label.grid_remove()
            self.y0_entry.grid_remove()

    def update_methods(self, event):
        eq_type = self.type_var.get()
        self.method_combo['values'] = self.methods.get(eq_type, [])
        self.method_combo.set("")

    def update_graph(self, event):
        self.plot_graph_based_on_equation()

    def refresh_graph(self):
        self.plot_graph_based_on_equation()

    def linspace(self, start, stop, num):
        if num < 2:
            return [start]
        if start == stop:
            return [start] * num
        step = (stop - start) / (num - 1)
        return [start + i * step for i in range(num)]

    def verify_root_existence(self, eq, a, b, tol=1e-6):
        try:
            eq_instance = Eq(eq)
            fa = eq_instance.safe_eval(a)
            fb = eq_instance.safe_eval(b)

            if fa * fb > 0:
                return False, "Корень на интервале отсутствует или их несколько."
            elif abs(fa - fb) < tol:
                return False, "Интервал слишком мал для определения корня."

            n_points = 1000
            x_vals = self.linspace(a, b, n_points)
            y_vals = [eq_instance.safe_eval(x) for x in x_vals]
            sign_changes = 0
            for i in range(len(y_vals) - 1):
                if y_vals[i] * y_vals[i + 1] < 0:
                    sign_changes += 1
            if sign_changes > 1:
                return False, "На интервале несколько корней или их нет."

            return True, "Корень существует."
        except ZeroDivisionError:
            return False, "Ошибка: деление на ноль при верификации корня."
        except Exception as e:
            return False, f"Ошибка верификации: {str(e)}"

    def verify_system_root_existence(self, eq_str, a, b, tol=1e-6):
        try:
            eqs = eq_str.split(", ")
            if len(eqs) != 2:
                return False, "Ошибка: система должна содержать два уравнения, разделённых ', '", []

            system = SystemEq(eqs)
            y_min, y_max = -5, 5  # Определяем интервал для y
            n_points_x = 50
            n_points_y = 50
            x_vals = self.linspace(a, b, n_points_x)
            y_vals = self.linspace(y_min, y_max, n_points_y)

            # Ищем точки, где f1 и f2 близки к нулю
            intersections = []
            for x in x_vals:
                for y in y_vals:
                    f1 = system.safe_eval(x, y, 0)
                    f2 = system.safe_eval(x, y, 1)
                    if abs(f1) < 0.1 and abs(f2) < 0.1:  # Если обе функции близки к 0
                        intersections.append((x, y))

            if not intersections:
                return False, "Корень на интервале отсутствует.", []
            elif len(intersections) > 1:
                return True, "На интервале несколько корней.", intersections
            else:
                return True, "Корень существует.", intersections

        except Exception as e:
            return False, f"Ошибка верификации системы: {str(e)}", []

    def newton_system(self, x_min, x_max, eq_str, tol):
        eqs = eq_str.split(", ")
        if len(eqs) != 2:
            return "Ошибка: строка должна содержать два уравнения, разделённых ', '"

        print(f"Уравнения в newton_system: {eqs}")

        try:
            x0 = float(self.x0_var.get())
            y0 = float(self.y0_var.get())
        except ValueError:
            return "Ошибка: некорректное начальное приближение (x0 или y0)."

        # Проверяем количество корней
        has_root, message, intersections = self.verify_system_root_existence(eq_str, x_min, x_max, tol)

        system = SystemEq(eqs)

        # Пробуем несколько начальных приближений
        all_roots = []
        y_variations = [y0 - 1.0, y0, y0 + 1.0]
        x_attempts = [x0]
        if intersections:
            for x, y in intersections:
                x_attempts.append(x)
                y_variations.append(y)
        x_attempts.append((x_min + x_max) / 2)

        for x_start in x_attempts:
            for y_var in y_variations:
                try:
                    x_root, y_root = system.newton_system(x_start, y_var, tol)
                    if x_min <= x_root <= x_max:
                        f1_val = system.safe_eval(x_root, y_root, 0)
                        f2_val = system.safe_eval(x_root, y_root, 1)
                        if abs(f1_val) < tol and abs(f2_val) < tol:
                            is_unique = True
                            for rx, ry in all_roots:
                                if abs(x_root - rx) < tol and abs(y_root - ry) < tol:
                                    is_unique = False
                                    break
                            if is_unique:
                                all_roots.append((x_root, y_root))
                except Exception as e:
                    print(f"Метод Ньютона не сошёлся с x0={x_start}, y0={y_var}: {str(e)}")
                    continue

        if not all_roots:
            return "Не удалось найти корень на заданном интервале."
        elif len(all_roots) > 1:
            return "На интервале несколько корней."
        else:
            return f"Найденный корень: x = {all_roots[0][0]:.6f}, y = {all_roots[0][1]:.6f}"

    def load_from_file(self):
        from tkinter import filedialog
        file_path = filedialog.askopenfilename(filetypes=[("Text files", "*.txt")])
        if file_path:
            try:
                with open(file_path, 'r', encoding='utf-8') as file:
                    lines = file.readlines()
                    for line in lines:
                        line = line.strip()
                        if line and not line.startswith('#'):
                            values = [i.replace(',', '.') for i in line.split(',')]
                            print(values)
                            if len(values) == 5:
                                try:
                                    x_min = float(values[0].strip())
                                    x_max = float(values[1].strip())
                                    error = float(values[2].strip())
                                    x0 = float(values[3].strip())
                                    y0 = float(values[4].strip())
                                    if x_min < x_max and error > 0:
                                        self.x_min.set(x_min)
                                        self.x_max.set(x_max)
                                        self.error.set(error)
                                        if self.type_var.get() == "Система НУ":
                                            self.x0_var.set(x0)
                                            self.y0_var.set(y0)
                                        self.result_label.config(
                                            text=f"Параметры успешно загружены: от {x_min} до {x_max}, погрешность {error}, x0 = {x0}, y0 = {y0}")
                                    else:
                                        self.result_label.config(text="Ошибка: x_min >= x_max или погрешность <= 0")
                                except ValueError as ve:
                                    self.result_label.config(
                                        text=f"Ошибка: некорректный формат чисел в файле: {str(ve)}")
                            else:
                                self.result_label.config(
                                    text="Ошибка: файл должен содержать пять чисел (x_min, x_max, error, x0, y0) через запятую")
            except Exception as e:
                self.result_label.config(text=f"Ошибка загрузки файла: {str(e)}")

    def solve(self):
        try:
            eq_type = self.type_var.get()
            equation = self.eq_var.get()
            method = self.method_var.get()
            x_min = self.x_min.get()
            x_max = self.x_max.get()
            error = self.error.get()

            if not all([eq_type, equation, method, x_min, x_max, error]):
                raise ValueError("Заполните все поля")
            if x_min >= x_max:
                raise ValueError("Начало интервала должно быть меньше конца")
            if error <= 0:
                raise ValueError("Погрешность должна быть положительной")

            self.iter_count = 0

            if eq_type == "НУ":
                has_root, message = self.verify_root_existence(equation, x_min, x_max)
                if not has_root:
                    self.result_label.config(text=message)
                    return

                self.current_eq = Eq(equation)

                fa = self.current_eq.safe_eval(x_min)
                fb = self.current_eq.safe_eval(x_max)
                if abs(fa) < abs(fb):
                    x0 = x_min + 0.1 * (x_max - x_min)
                else:
                    x0 = x_max - 0.1 * (x_max - x_min)

                if method == "Метод хорд":
                    result, iterations = self.current_eq.hord(x_min, x_max, error, track_iter=True)
                elif method == "Метод секущих":
                    result, iterations = self.current_eq.secant(x_min, x_max, error, track_iter=True)
                elif method == "Метод простой итерации":
                    success, message, k = self.check_simple_iteration_convergence(x_min, x_max, error)
                    if not success:
                        self.result_label.config(text=message)
                        return
                    if k is None:
                        self.result_label.config(text="Не удалось найти подходящий k для сходимости")
                        return
                    result, iterations = self.current_eq.simple_iteration(x0, error, x_min, x_max, k, track_iter=True)
                self.display_result(result, self.current_eq.safe_eval(result), iterations, method, equation)
            else:  # Система НУ
                eqs = equation.split(", ")
                if len(eqs) != 2:
                    self.result_label.config(text="Ошибка: для системы НУ нужно указать два уравнения, разделённых ', '")
                    return
                result = self.newton_system(x_min, x_max, equation, error)
                self.display_result(result, None, None, method, equation)

        except ZeroDivisionError:
            self.result_label.config(text="Ошибка: деление на ноль при решении.")
        except ValueError as e:
            self.result_label.config(text=f"Ошибка: {str(e)}")
        except Exception as e:
            self.result_label.config(text=f"Неизвестная ошибка: {str(e)}")

    def check_simple_iteration_convergence(self, a, b, tol):
        try:
            if a >= b:
                return False, "Ошибка: a должно быть меньше b", None

            eq_instance = Eq(self.eq_var.get())
            h = 1e-7
            x_vals = self.linspace(a, b, 100)

            k = 0.1
            max_attempts = 10
            attempt = 0
            while attempt < max_attempts:
                max_phi_derivative = 0
                for x in x_vals:
                    fx = eq_instance.safe_eval(x)
                    fx_h = eq_instance.safe_eval(x + h)
                    if fx is None or fx_h is None:
                        return False, f"Ошибка: невозможно вычислить функцию в точке x = {x}", None
                    f_derivative = (fx_h - fx) / h
                    phi_derivative = 1 - k * f_derivative
                    max_phi_derivative = max(max_phi_derivative, abs(phi_derivative))

                if max_phi_derivative < 1:
                    return True, f"Условие сходимости выполнено с k={k}", k
                k *= 0.5
                attempt += 1

            return False, f"Условие сходимости не выполнено: max |phi'(x)| = {max_phi_derivative} >= 1", None

        except ZeroDivisionError:
            return False, "Ошибка: деление на ноль при вычислении производной", None
        except Exception as e:
            return False, f"Неизвестная ошибка: {str(e)}", None

    def display_result(self, result, f_value, iterations, e, m):
        output = ""

        if isinstance(result, str):
            output = result
            output += f"\nУравнение: {m}\n"
            output += f"Уравнение решенное методом: {e}\n"
        else:
            output += f"\nУравнение: {m}\n"
            output += f"Уравнение решенное методом: {e}\n"
            output = f"\nНайденный корень: x = {result:.6f}\n"
            if f_value is not None:
                output += f"\nУравнение: {e}\n"
                output += f"Уравнение решенное методом: {m}\n"
                output += f"\nЗначение функции в корне: {f_value:.6f}\n"
            if iterations is not None:
                output += f"\nКоличество итераций: {iterations}\n"
        if self.save_to_file_var.get():

            try:
                # Открываем диалог выбора файла
                root = tk.Tk()
                root.withdraw()  # Скрываем основное окно

                file_path = filedialog.asksaveasfilename(
                    defaultextension=".txt",
                    filetypes=[("Text files", "*.txt"), ("All files", "*.*")],
                    title="Сохранить результаты"
                )

                if file_path:  # Если пользователь выбрал файл
                    with open(file_path, "w", encoding="utf-8") as f:
                        f.write(output)
                    self.result_label.config(text=f"Результаты сохранены в {file_path}")
                else:  # Если пользователь отменил выбор
                    self.result_label.config(text="Сохранение отменено пользователем")

            except Exception as e:
                self.result_label.config(text=f"Ошибка сохранения в файл: {str(e)}")
        else:
            self.result_label.config(text=output)

    def plot_graph_based_on_equation(self):
        equation = self.eq_var.get()
        x_min = self.x_min.get()
        x_max = self.x_max.get()
        error = self.error.get()

        if not equation or x_min >= x_max:
            self.result_label.config(text="Ошибка: некорректный интервал или уравнение не выбрано.")
            return

        for widget in self.canvas_frame.winfo_children():
            widget.destroy()

        try:
            fig, ax = plt.subplots()
            x = self.linspace(x_min, x_max, 1000)

            if self.type_var.get() == "НУ":
                self.current_eq = Eq(equation)
                y = []
                roots = []
                for xi in x:
                    val = self.current_eq.safe_eval(xi)
                    y.append(min(max(val, -1e6), 1e6))

                has_root, message = self.verify_root_existence(equation, x_min, x_max)
                if has_root:
                    root, _ = self.current_eq.hord(x_min, x_max, error, track_iter=True)
                    roots.append(root)

                ax.plot(x, y, label="Уравнение")
                if roots:
                    ax.scatter(roots, [0] * len(roots), color='red', zorder=5, label='Корень')

                y_min = min(y)
                y_max = max(y)
                padding = 0.1 * (y_max - y_min) if y_max != y_min else 1.0
                ax.set_ylim(y_min - padding, y_max + padding)

            else:  # Система НУ
                y_x = equation.split(", ")
                if len(y_x) != 2:
                    self.result_label.config(text="Ошибка: система должна содержать два уравнения.")
                    return

                system = SystemEq(y_x)
                y0 = float(self.y0_var.get()) if self.y0_var.get() else 0.0
                x0 = float(self.x0_var.get()) if self.x0_var.get() else 0.0

                # Определяем сетку для построения контурных линий
                y_min, y_max = -5, 5  # Расширим интервал по y
                x_vals = np.linspace(x_min, x_max, 100)
                y_vals = np.linspace(y_min, y_max, 100)
                X, Y = np.meshgrid(x_vals, y_vals)
                F1 = np.zeros_like(X)
                F2 = np.zeros_like(X)

                for i in range(X.shape[0]):
                    for j in range(X.shape[1]):
                        F1[i, j] = system.safe_eval(X[i, j], Y[i, j], 0)
                        F2[i, j] = system.safe_eval(X[i, j], Y[i, j], 1)

                # Строим контурные линии для f1(x, y) = 0 и f2(x, y) = 0
                contour1 = ax.contour(X, Y, F1, levels=[0], colors='blue')
                contour2 = ax.contour(X, Y, F2, levels=[0], colors='green')

                # Создаём легенду вручную
                from matplotlib.lines import Line2D
                legend_elements = [
                    Line2D([0], [0], color='blue', lw=2, label='f1(x, y) = 0'),
                    Line2D([0], [0], color='green', lw=2, label='f2(x, y) = 0')
                ]

                # Находим корни
                roots_x = []
                roots_y = []
                n_points = 30
                x_test = self.linspace(x_min, x_max, n_points)
                y_variations = [y0 - 1.0, y0, y0 + 1.0]

                for i in range(n_points - 1):
                    x_start = x_test[i]
                    x_end = x_test[i + 1]
                    has_root, message, intersections = self.verify_system_root_existence(equation, x_start, x_end, error)
                    if has_root and intersections:
                        for x_start, y_var in intersections:
                            try:
                                x_root, y_root = system.newton_system(x_start, y_var, error)
                                if x_min <= x_root <= x_max and y_min <= y_root <= y_max:
                                    f1_val = system.safe_eval(x_root, y_root, 0)
                                    f2_val = system.safe_eval(x_root, y_root, 1)
                                    if abs(f1_val) < error and abs(f2_val) < error:
                                        is_unique = True
                                        for rx, ry in zip(roots_x, roots_y):
                                            if abs(x_root - rx) < error and abs(y_root - ry) < error:
                                                is_unique = False
                                                break
                                        if is_unique:
                                            roots_x.append(x_root)
                                            roots_y.append(y_root)
                            except Exception as e:
                                print(f"Метод Ньютона не сошёлся на подинтервале [{x_start}, {x_end}] с y={y_var}: {str(e)}")
                                continue

                # Отображаем корень только если он один
                if len(roots_x) == 1:
                    ax.scatter(roots_x, roots_y, color='red', zorder=5, s=100, marker='o')
                    ax.annotate(f'({roots_x[0]:.3f}, {roots_y[0]:.3f})', (roots_x[0], roots_y[0]), textcoords="offset points", xytext=(0, 10), ha='center', fontsize=8)
                    legend_elements.append(Line2D([0], [0], marker='o', color='w', markerfacecolor='red', markersize=10, label='Корень'))

                # Добавляем легенду
                ax.legend(handles=legend_elements)

                ax.set_xlabel('x')
                ax.set_ylabel('y')

            ax.axhline(y=0, color='k', linestyle='-', alpha=0.3)
            ax.axvline(x=0, color='k', linestyle='-', alpha=0.3)
            ax.grid(True)
            ax.set_xlim(x_min, x_max)

            canvas = FigureCanvasTkAgg(fig, master=self.canvas_frame)
            canvas.draw()
            canvas.get_tk_widget().pack(fill="both", expand=True)

        except ZeroDivisionError:
            self.result_label.config(text="Ошибка: деление на ноль при построении графика.")
        except Exception as e:
            self.result_label.config(text=f"Ошибка построения графика: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = EquationSolverApp(root)
    root.mainloop()