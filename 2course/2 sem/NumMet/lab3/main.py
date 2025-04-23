from math import *


def dict_print(dict):
    [print(f"{i}: {dict[i]}") for i in range(1, len(dict) + 1)]


def create_dict(array_str):
    dict = {}
    for i in range(1, len(array_str) + 1):
        dict[i] = array_str[i - 1]
    return dict


def from_user_view_to_python_view_function(func):
    func = func.replace("^", "**")
    return lambda x: eval(func)


def method_rectangle(what_is_method_rectangle, func, a, b, tol, n):
    match what_is_method_rectangle:
        case 1:
            while True:
                h1 = (b - a) / n
                h2 = (b - a) / (2 * n)

                I_h = sum(func(a + i * h1) for i in range(n))
                I_h2 = sum(func(a + i * h2) for i in range(2 * n))

                I_h *= h1
                I_h2 *= h2

                error = abs(I_h2 - I_h) / (2 - 1)
                # print(f"n = {n}, I_h = {I_h:.6f}, I_h2 = {I_h2:.6f}, Runge Error = {error:.6f}")

                if error < tol:
                    print(f"Значение интеграла: {I_h}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
                    return I_h2
                else:
                    n *= 2
        case 2:
            while True:
                h1 = (b - a) / ( n)
                h2 = (b - a) / (2 * n)

                I_h = sum(func(a + i*h1 + h1/2) for i in range(n))
                I_h2 = sum(func(a + i*h2 + h2/2) for i in range(2 * n))

                I_h *= h1
                I_h2 *= h2

                error = abs(I_h2 - I_h) / (2 - 1)
                # print(f"n = {n}, I_h = {I_h:.6f}, I_h2 = {I_h2:.6f}, Runge Error = {error:.6f}")

                if error < tol:
                    print(f"Значение интеграла: {I_h}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
                    return I_h
                else:
                    n *= 2
        case 3:
            while True:
                h1 = (b - a) / (n)
                h2 = (b - a) / (2 * n)

                I_h = sum(func(a + i * h1) for i in range(1, n))
                I_h2 = sum(func(a + i * h2) for i in range(1, 2 * n))

                I_h *= h1
                I_h2 *= h2

                error = abs(I_h2 - I_h) / (2 - 1)
                # print(f"n = {n}, I_h = {I_h:.6f}, I_h2 = {I_h2:.6f}, Runge Error = {error:.6f}")

                if error < tol:
                    print(f"Значение интеграла: {I_h}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
                    return I_h2
                else:
                    n *= 2

def trapezoidal_method(f, a, b, tol=1e-6, n=4):
    """
    Метод трапеций с автоматическим уточнением по правилу Рунге до достижения заданной точности.
    """
    p = 2  # порядок метода трапеций

    def single_trapezoid(f, a, b, n):
        h = (b - a) / n
        result = 0.5 * (f(a) + f(b))
        for i in range(1, n):
            result += f(a + i * h)
        return result * h

    I_prev = single_trapezoid(f, a, b, n)

    while True:
        n *= 2
        I_curr = single_trapezoid(f, a, b, n)
        I_runge = I_curr + (I_curr - I_prev) / (2**p - 1)
        if abs(I_runge - I_curr) < tol:
            print(f"Значение интеграла: {I_curr}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
            return I_runge
        I_prev = I_curr


def simpson_method(f, a, b, tol=1e-6, n=4):

    p = 4  # порядок метода Симпсона

    def single_simpson(f, a, b, n):
        if n % 2 != 0:
            n += 1
        h = (b - a) / n
        result = f(a) + f(b)
        for i in range(1, n, 2):
            result += 4 * f(a + i * h)
        for i in range(2, n-1, 2):
            result += 2 * f(a + i * h)
        return result * h / 3

    I_prev = single_simpson(f, a, b, n)
    while True:
        n *= 2
        I_curr = single_simpson(f, a, b, n)
        I_runge = I_curr + (I_curr - I_prev) / (2**p - 1)
        if abs(I_runge - I_curr) < tol:
            print(f"Значение интеграла: {I_curr}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
            return I_runge
        I_prev = I_curr


dict_func = create_dict(["-x^3-x^2-2*x+1",
                         "e^(-x)",
                         "x*0.00001 + e**(0.00014125*log(x))",
                         "4*x^sin(x)+ 5*x^2+3*x - 0.0000094198418", ])


dict_print(dict_func)
input_user_key_function = input("\nВыберите по числу какая функция вам по нраву, моя хозяина:\n")

while input_user_key_function not in ["1", "2", "3", "4"]:
    print("Хозяина, введите нормальная число!")
    input_user_key_function = input("\nВыберите по числу какая функция вам по нраву, моя хозяина:\n")

# --------------Обработка интервала--------------
interval = []
while True:
    try:
        input_user_interval_integer = input(
            "Введите пределы интегрирования моя хозяина!\nДля [a,b] введите a и b через пробел: \'a b\'\n")

        interval = input_user_interval_integer.replace(",", ".").strip(" ").split()
        interval = list(map(float, interval))
        if len(interval) != 2:
            print("Введите нормальная интервала!")
            continue
        print(interval)
        break
    except Exception as _:
        print("Введите нормальная числа интервала!")
        continue
print(interval)
# --------------Обработка интервала--------------

# --------------Обработка погрешности--------------
tol = 0
while True:
    try:
        tol = input("Введите погрешность моя хозяина:")
        tol = tol.replace(",", ".")
        tol = float(tol)
        break
    except Exception as _:
        print("Введите нормальная числа погрешностя!")
        continue
# --------------Обработка погрешности--------------

# --------------Обработка начального числа разбиения--------------
n = 4
while True:
    try:
        n = input("Введите начальное разбиение моя хозяина:")
        n = n.replace(",", ".")
        n = int(n)
        break
    except Exception as _:
        print("Введите нормальная числа погрешностя!")
        continue
# --------------Обработка начального числа разбиения--------------

dict_methods = create_dict(["Метод прямоугольника (дальше будет на выбор 3 вида)", "Метод трапеций", "Метод Симпсона"])

input_user_method = 0
input_user_method_rectangle = 0
while True:
    try:
        dict_print(dict_methods)
        input_user_method = input("Выберите по числу какая метода вам по нраву, моя хозяина:\n")
        input_user_method = int(input_user_method)
        if input_user_method not in range(1, 4):
            print("Число не в диапазоне из выбора, хозяина, выберить нормально!")
            continue
        if input_user_method == 1:
            dict_methods_rectangle = create_dict([
                "Левые прямоугольники",
                "Средние прямоугольники",
                "Правые прямоугольники",
            ])
            dict_print(dict_methods_rectangle)
            input_user_method_rectangle = input(
                "Выберите по числу какая метода прямоугольника вам по нраву, моя хозяина:\n")
            input_user_method_rectangle = int(input_user_method_rectangle)
            if input_user_method_rectangle not in range(1, 4):
                print("Число не в диапазоне из выбора, хозяина, выберить нормально!")
                continue
            break
        break
    except ValueError as _:
        print("На число это не походить хозяина, ввести нормальная числа!")
        continue
print(input_user_method)
from_user_view_to_python_view_function(dict_func[1])

if(interval[0] == interval[1]):
    print(f"Значение интеграла: {0}\nТочность:{tol}\nЧисло разбиений: {n}\n ")
    exit(0)

match input_user_method:
    case 1:
        method_rectangle(
            what_is_method_rectangle=input_user_method_rectangle,
            func=from_user_view_to_python_view_function(dict_func[int(input_user_key_function)]),
            a=interval[0],
            b=interval[1],
            tol=tol,
            n=n
        )
    case 2:
        trapezoidal_method(
            f=from_user_view_to_python_view_function(dict_func[int(input_user_key_function)]),
            a=interval[0],
            b=interval[1],
            tol=tol,
            n=n
        )
    case 3:
        simpson_method(
            f=from_user_view_to_python_view_function(dict_func[int(input_user_key_function)]),
            a=interval[0],
            b=interval[1],
            tol=tol,
            n=n
        )