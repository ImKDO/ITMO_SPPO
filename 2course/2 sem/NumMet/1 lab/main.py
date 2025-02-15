# Получаем с обработкой n
import itertools
import math

def euclidean_norm(matrix):
    norm = 0
    for row in matrix:
        for element in row:
            norm += element ** 2
    return norm ** 0.5

def get_n(type_input, file_content=None):
    if type_input == "i":
        while True:
            print("Введите размерность n <= 20 матрицы nxn")
            try:
                n_check = float(input().strip())
                n = int(n_check)
                if abs(n) != n:
                    print("n не может быть отрицательным")
                    continue
                if (n - n_check) != 0:
                    print("n не может быть дробным")
                    continue
            except ValueError:
                print("Введите правильное значение n")
                continue
            if(n > 20):
                print("Размерность n должна быть <= 20")
                continue
            return n
    else:
        n = 0
        try:
            n = int(file_content)
            return n
        except ValueError:
            print("Введите валидную размерность")
            exit(1)

# Получаем с обработкой точность
def get_accuracy(input_str, file_content=None):
    if input_str == "i":
        acc = 0
        while True:
            print("Введите точность (степень 10) ")
            try:
                acc = int(input().strip())
            except ValueError:
                print("Введите валидную точность")
                continue
            return 10**(-acc)
    else:
        accuracy = 0
        try:
            accuracy = int(file_content[n + 1])
        except ValueError:
            print("Введите валидную точность")
            exit(1)
        return 10**(-accuracy)

# Получаем с обработкой матрицу
def print_matrix(matrix):
    for row in matrix:
        for j in range(len(row)):
            print(row[j], end=" ")
        print("")

def get_matrix(input_str, n, file_content=None):
    matrix = []
    b_vec = []
    if input_str == "i":
        print(f"Введите матрицу размера {n}x{n+1}")
        while len(matrix) != n:
            strings = []
            try:
                strings = list(input().strip().split())
                strings = [float(i.replace(",", ".")) for i in strings]
                b_vec.append(strings[n])
                if len(strings) != (n+1) :
                    print(f"Введите строку размера {n+1}")
                    continue
            except ValueError:
                return [], [], False
            matrix.append(strings)
        return b_vec, matrix, True
    else:
        for strings in range(1, n + 1):
            try:
                vec = list(map(float, file_content[strings].split()))
                b_vec.append(vec[n])
                if len(vec) != (n+1) :
                    print(f"В файле замечена строка размера != {n+1}")
                    exit(1)
                vec.pop(n)
                matrix.append(vec)
            except ValueError:
                print("Невалидные значения для матрицы")
                exit(1)
        return b_vec, matrix

def check_diagonal_predominance(A):
    for i in range(len(A)):
        row_sum = sum(abs(A[i][j]) for j in range(len(A)) if i != j)
        if abs(A[i][i]) < row_sum:
            return False
    return True


def permute_matrix_dynamic(matrix):
    def permute(arr, l, r):
        if l == r:
            yield [row[:] for row in arr]
        else:
            for i in range(l, r):
                arr[l], arr[i] = arr[i], arr[l]  # Swap rows
                yield from permute(arr, l + 1, r)
                arr[l], arr[i] = arr[i], arr[l]  # Swap back

    yield from permute(matrix, 0, len(matrix))

def go_to_itteration(vec_x, base_reccurent):
    vec_approx = []
    for i in range(len(base_reccurent)):
        vec_sol = []
        sum = 0
        for j in range(len(vec_x)):
            sum += base_reccurent[i]*vec_x[i][j]
        vec_sol.append(sum)
        vec_approx.append(vec_sol)


def method_Gauss_Zeidel(A, b, acc):
    A = [row[:] for row in A]
    b = b[:]

    flag = False
    for perm in permute_matrix_dynamic(A):
        # Переставляем b так же, как A, по правильным индексам
        perm_b = [b[i] for i in range(len(A)) if A[i] in perm]

        print("-------------------------------------------------------")
        for row, b_val in zip(perm, perm_b):
            print(row, "→", b_val)  # Теперь показывает правильно!
        print("-------------------------------------------------------")

        if check_diagonal_predominance(perm):
            A = [row[:] for row in perm]
            b = perm_b[:]
            flag = True
            break

    if not flag:
        print("Система не достигает диагонального преобладания, метод может не сойтись.")
        return None

    # Начальное приближение (нулевой вектор)
    x = [0] * len(A)

    # Итерации
    diff = float('inf')
    k = 0
    while diff > acc:
        x_old = x[:]
        for i in range(len(A)):
            sum1 = sum(A[i][j] * x[j] for j in range(i))  # Сумма до i
            sum2 = sum(A[i][j] * x_old[j] for j in range(i + 1, len(A)))  # Сумма после i
            x[i] = (b[i] - sum1 - sum2) / A[i][i]  # Обновляем x[i]
            print(f"Вектор погрешности: {x}")
            k+=1

        diff = max(abs(x[i] - x_old[i]) for i in range(len(A)))
    print(f"Норма матрицы:{euclidean_norm(A)}")
    print(f"Количество иттераций:{k}")
    print(f"Вектор решений:{x}")

    return x


start_string =\
"""Каким способом хотите вводить данные?
i - ввод с клавиатуры
f - данные считываются из файла"""
print(start_string)


input_str = input().strip()

if input_str == "i":
    # Получаем размерность матрицы
    n = get_n(input_str)

    # Формируем матрицу
    #---------------------------------------------------------
    matrix = []
    b_vec = []
    while True:
        b_vec, matrix, valid_input_matrix = get_matrix(input_str,n)
        if not valid_input_matrix:
            print("Введите правильные значения для матрицы")
            continue
        break
    #---------------------------------------------------------

    # Получаем точность
    accuracy = get_accuracy(input_str)

    print(method_Gauss_Zeidel(matrix, b_vec, accuracy))


elif input_str == "f":
    start_string =\
    """
    Формат введения данных через файл
    
    Размерность (n)
    Матрица (nx(n+1))
    Точность
    """

    print(start_string)

    n = 0
    matrix = []
    accuracy = 0
    while True:

        print("Укажите путь к файлу")
        file_path = input().strip()

        accuracy = 0
        n = 0
        matrix = []
        b_vec = []

        try:
            with open(file_path, "r") as file:
                file_open = file.read()
                file_content = [line.replace(',', '.') for line in file_open.splitlines()]

                n = get_n(input_str, file_content[0])
                b_vec, matrix = get_matrix(input_str, n, file_content)
                accuracy = get_accuracy(input_str, file_content)
                break
        except FileNotFoundError:
            print("По данному пути не существует файла")
        except PermissionError:
            print("Недостаточно прав для открытия файла")

    print(method_Gauss_Zeidel(matrix, b_vec, accuracy))

else:
    print("СКИИИИИИИИИИИИИП")