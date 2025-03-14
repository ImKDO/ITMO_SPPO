import tkinter as tk
from tkinter import ttk, messagebox
import math

class IntegrationCalculator:
    def __init__(self, root):
        self.root = root
        self.root.title("Numerical Integration Calculator")
        self.root.geometry("500x500")

        # Variables
        self.function_var = tk.StringVar()
        self.a_var = tk.StringVar(value="0")
        self.b_var = tk.StringVar(value="1")
        self.tolerance_var = tk.StringVar(value="0.00001")
        self.method_var = tk.StringVar(value="Simpson")

        # GUI Elements
        tk.Label(root, text="Function (e.g., x**2, math.sin(x)):").grid(row=0, column=0, padx=5, pady=5)
        tk.Entry(root, textvariable=self.function_var).grid(row=0, column=1, padx=5, pady=5)

        tk.Label(root, text="Lower Limit (a):").grid(row=1, column=0, padx=5, pady=5)
        tk.Entry(root, textvariable=self.a_var).grid(row=1, column=1, padx=5, pady=5)

        tk.Label(root, text="Upper Limit (b):").grid(row=2, column=0, padx=5, pady=5)
        tk.Entry(root, textvariable=self.b_var).grid(row=2, column=1, padx=5, pady=5)

        tk.Label(root, text="Tolerance:").grid(row=3, column=0, padx=5, pady=5)
        tk.Entry(root, textvariable=self.tolerance_var).grid(row=3, column=1, padx=5, pady=5)

        tk.Label(root, text="Method:").grid(row=4, column=0, padx=5, pady=5)
        methods = ["Left Rectangle", "Right Rectangle", "Midpoint", "Trapezoidal", "Simpson"]
        ttk.Combobox(root, textvariable=self.method_var, values=methods).grid(row=4, column=1, padx=5, pady=5)

        tk.Button(root, text="Calculate", command=self.calculate).grid(row=5, column=0, columnspan=2, pady=10)

        # Result Display
        self.result_text = tk.Text(root, height=10, width=60)
        self.result_text.grid(row=6, column=0, columnspan=2, padx=5, pady=5)

    def parse_function(self, func_str):
        """Parse the input function string into a callable function using eval."""
        def f(x):
            # Define a safe namespace with math functions
            safe_dict = {
                "x": x,
                "math": math,
                "sin": math.sin,
                "cos": math.cos,
                "tan": math.tan,
                "exp": math.exp,
                "log": math.log,
                "sqrt": math.sqrt,
                "pi": math.pi,
                "e": math.e
            }
            try:
                return eval(func_str.replace(",","."), {"__builtins__": None}, safe_dict)
            except Exception as e:
                raise ValueError(f"Invalid function: don't like func")
        return f

    def left_rectangle(self, f, a, b, n):
        """Left Rectangle Method."""
        dx = (b - a) / n
        approx = sum(f(a + i * dx) for i in range(n)) * dx
        values = [f(a + i * dx) for i in range(n + 1)]
        return approx, values

    def right_rectangle(self, f, a, b, n):
        """Right Rectangle Method."""
        dx = (b - a) / n
        approx = sum(f(a + (i + 1) * dx) for i in range(n)) * dx
        values = [f(a + i * dx) for i in range(n + 1)]
        return approx, values

    def midpoint(self, f, a, b, n):
        """Midpoint Rectangle Method."""
        dx = (b - a) / n
        approx = sum(f(a + (i + 0.5) * dx) for i in range(n)) * dx
        values = [f(a + i * dx) for i in range(n + 1)]
        return approx, values

    def trapezoidal(self, f, a, b, n):
        """Trapezoidal Method."""
        dx = (b - a) / n
        approx = (f(a) + f(b)) / 2
        for i in range(1, n):
            approx += f(a + i * dx)
        approx *= dx
        values = [f(a + i * dx) for i in range(n + 1)]
        return approx, values

    def simpson(self, f, a, b, n):
        """Simpson's Rule (requires n to be even)."""
        if n % 2 != 0:
            n += 1  # Ensure n is even
        dx = (b - a) / n
        approx = f(a) + f(b)
        for i in range(1, n):
            approx += 4 * f(a + i * dx) if i % 2 else 2 * f(a + i * dx)
        approx *= dx / 3
        values = [f(a + i * dx) for i in range(n + 1)]
        return approx, values

    def adaptive_quadrature(self, f, a, b, method_func, p, tol):
        """Adaptive quadrature starting with n=4."""
        n = 4
        while True:
            approx_n, values_n = method_func(f, a, b, n)
            approx_2n, values_2n = method_func(f, a, b, 2 * n)
            error = abs(approx_n - approx_2n) / (2**p - 1)
            if error < tol or n > 1000000:
                return approx_2n, 2 * n, values_2n, error
            n *= 2

    def calculate(self):
        """Handle the calculation and display results."""
        try:
            # Get input values
            func_str = self.function_var.get()
            a = float(self.a_var.get().replace(",", "."))
            b = float(self.b_var.get().replace(",", "."))
            tol = float(self.tolerance_var.get().replace(",", "."))
            method = self.method_var.get()

            # Ensure a < b
            if a >= b:
                raise ValueError("Lower limit must be less than upper limit.")

            # Ensure tolerance is positive
            if tol <= 0:
                raise ValueError("Tolerance must be positive.")

            # Parse function
            f = self.parse_function(func_str)

            # Select method and order of convergence (p)
            method_funcs = {
                "Left Rectangle": (self.left_rectangle, 1),
                "Right Rectangle": (self.right_rectangle, 1),
                "Midpoint": (self.midpoint, 2),
                "Trapezoidal": (self.trapezoidal, 2),
                "Simpson": (self.simpson, 4)
            }
            method_func, p = method_funcs[method]

            # Perform adaptive quadrature
            result, n_intervals, values, error = self.adaptive_quadrature(f, a, b, method_func, p, tol)

            # Display results
            self.result_text.delete(1.0, tk.END)
            self.result_text.insert(tk.END, f"Method: {method}\n")
            self.result_text.insert(tk.END, f"Integral Value: {result:.6f}\n")
            self.result_text.insert(tk.END, f"Number of Intervals: {n_intervals}\n")
            self.result_text.insert(tk.END, f"Estimated Error: {error:.6e}\n")
            self.result_text.insert(tk.END, "Function Values at Breakpoints:\n")
            for i, val in enumerate(values):
                x_val = a + i * (b - a) / n_intervals
                self.result_text.insert(tk.END, f"x{i} = {x_val:.4f}, f(x{i}) = {val:.6f}\n")

        except ValueError as e:
            messagebox.showerror("Error", str(e))
        except Exception as e:
            messagebox.showerror("Error", f"An error occurred: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = IntegrationCalculator(root)
    root.mainloop()