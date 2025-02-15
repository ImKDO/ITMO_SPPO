# Получаем с обработкой n
import itertools
import math


def get_n(type_input, file_content=None):
    if type_input == "i":
        while True:
            print("Введите размерность n <= 20 матрицы nxn")
            try:
                n = int(input().strip())
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
            return acc
    else:
        accuracy = 0
        try:
            accuracy = int(file_content[n + 1])
        except ValueError:
            print("Введите валидную точность")
            exit(1)
        return accuracy

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
                strings = list(map(float, input().strip().split()))
                b_vec.append(strings[n])
                if len(strings) != (n+1) :
                    print(f"Введите строку размера {n+1}")
                    continue
            except ValueError:
                return [], [], False
            matrix.append(strings)
        # print(b_vec)
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
        # print(b_vec)
        # print_matrix(matrix)
        return b_vec, matrix

def check_diagonal_predominance(A):
    for i in range(len(A)):
        sum = 0
        for j in range(len(A)):
            if i != j:
                sum += abs(A[i][j])
        if abs(A[i][i]) < sum:
            return False
    return True

def permute_matrix_dynamic(matrix):
    for perm in itertools.permutations(matrix):
        yield perm

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
    vec_x = []
    # print_matrix(A)
    for i in range (len(A)):
        x_i = []
        # for j in range(len(A)):
        for j in range(len(A[i])):
            if j != i:
                x_i.append(-A[i][j]/A[i][i])
        x_i.insert(0,b[i]/A[i][i])
        vec_x.append(x_i)
    print_matrix(vec_x)
    base_vec_x = b.copy()

    flag = False
    for perm in permute_matrix_dynamic(A):
        for row in perm:
           flag = check_diagonal_predominance(row)
           if flag: break
        if flag: break
    if not flag:
        print("Эта система не достигает диагонального преобладания, пошел нахуй")




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
    # print_matrix(matrix)
    #---------------------------------------------------------

    # Получаем точность
    accuracy = get_accuracy()

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
                file_content = file_open.splitlines()

                n = get_n(input_str, file_content[0])
                b_vec, matrix = get_matrix(input_str, n, file_content)
                accuracy = get_accuracy(input_str, file_content)
                break
        except FileNotFoundError:
            print("По данному пути не существует файла")
    method_Gauss_Zeidel(matrix, b_vec, accuracy)

else:
    print("fuck you")