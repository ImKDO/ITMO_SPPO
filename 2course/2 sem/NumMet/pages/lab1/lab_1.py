import flet as ft
import itertools
import math


def euclidean_norm(matrix):
    norm = 0
    for row in matrix:
        for element in row:
            norm += element ** 2
    return norm ** 0.5


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
                arr[l], arr[i] = arr[i], arr[l]
                yield from permute(arr, l + 1, r)
                arr[l], arr[i] = arr[i], arr[l]

    yield from permute(matrix, 0, len(matrix))


def method_Gauss_Seidel(A, b, acc):
    A = [row[:] for row in A]
    b = b[:]

    flag = False
    for perm in permute_matrix_dynamic(A):
        perm_b = [b[i] for i in range(len(A)) if A[i] in perm]
        if check_diagonal_predominance(perm):
            A = [row[:] for row in perm]
            b = perm_b[:]
            flag = True
            break

    if not flag:
        return "Система не достигает диагонального преобладания, метод может не сойтись."

    x = [0] * len(A)
    diff = float('inf')
    k = 0
    iterations_info = []

    while diff > acc:
        x_old = x[:]
        for i in range(len(A)):
            sum1 = sum(A[i][j] * x[j] for j in range(i))
            sum2 = sum(A[i][j] * x_old[j] for j in range(i + 1, len(A)))
            x[i] = (b[i] - sum1 - sum2) / A[i][i]
        diff = max(abs(x[i] - x_old[i]) for i in range(len(A)))
        k += 1
        iterations_info.append(f"Итерация {k}: {x}")

    result = f"Норма матрицы: {euclidean_norm(A)}\n"
    result += f"Количество итераций: {k}\n"
    result += f"Вектор решений: {x}\n"
    result += "\nИстория итераций:\n" + "\n".join(iterations_info)
    return result


def show_lab_1(page: ft.Page):
    current_matrix = []  # Матрица A (n × n)
    b_vector = []  # Вектор b (n × 1)

    # UI элементы
    size_input = ft.TextField(width=100, height=50, hint_text="Размерность", keyboard_type=ft.KeyboardType.NUMBER)
    accuracy_input = ft.TextField(width=100, height=50, hint_text="Точность (10^-n)",
                                  keyboard_type=ft.KeyboardType.NUMBER)
    matrix_input = ft.Column(scroll=True)
    output_text = ft.Text()
    file_picker = ft.FilePicker(on_result=lambda e: load_matrix_from_file(e))

    def create_matrix_input(size, initialize_zeros=True):
        matrix_input.controls.clear()

        # Если нужно инициализировать нулями, создаём пустую матрицу и вектор
        if initialize_zeros:
            current_matrix.clear()
            b_vector.clear()
            for _ in range(size):
                current_matrix.append([0.0] * size)  # Матрица A
                b_vector.append(0.0)  # Вектор b

        # Проверяем, что current_matrix и b_vector имеют правильный размер
        if len(current_matrix) != size or any(len(row) != size for row in current_matrix) or len(b_vector) != size:
            current_matrix.clear()
            b_vector.clear()
            for _ in range(size):
                current_matrix.append([0.0] * size)
                b_vector.append(0.0)

        for i in range(size):
            row = ft.Row(spacing=10)
            for j in range(size + 1):  # +1 для вектора b
                text_field = ft.TextField(
                    width=80,
                    height=25,
                    text_align=ft.TextAlign.CENTER,
                    hint_text=f"[{i + 1},{j + 1}]" if j < size else f"b[{i + 1}]",
                    keyboard_type=ft.KeyboardType.NUMBER,
                    value=str(current_matrix[i][j]) if j < size else str(b_vector[i]),  # Устанавливаем текущие значения
                    on_change=lambda e, i=i, j=j: update_matrix_value(i, j, e.control.value),
                )
                row.controls.append(text_field)
            matrix_input.controls.append(row)
        page.update()

    def update_matrix_value(row, col, value):
        try:
            if value.strip() == "":
                value = "0.0"

            if col < len(current_matrix[0]):  # Обновляем матрицу A
                current_matrix[row][col] = float(value)
            else:  # Обновляем вектор b (col будет равен size)
                b_vector[row] = float(value)
            page.update()  # Обновляем интерфейс после каждого изменения
        except (ValueError, IndexError) as ex:
            output_text.value = f"Ошибка: {ex}"
            page.update()

    def update_matrix_dimensions(e):
        try:
            size = int(size_input.value)
            if size > 20:
                output_text.value = "Размерность матрицы не должна превышать 20"
                page.update()
                return
            create_matrix_input(size)
            output_text.value = "Размерность обновлена!"
            page.update()
        except ValueError:
            output_text.value = "Введите корректное значение для размерности!"
            page.update()

    def load_matrix_from_file(e: ft.FilePickerResultEvent):
        if e.files:
            try:
                with open(e.files[0].path, "r", encoding="utf-8") as f:
                    content = [line.strip() for line in f.readlines() if line.strip()]  # Удаляем пустые строки

                # Проверяем, достаточно ли строк в файле
                if len(content) < 2:
                    raise ValueError("Файл должен содержать размерность, точность и данные матрицы")

                # Парсинг размера матрицы (первая строка)
                size = int(content[0])
                if size > 20 or size <= 0:
                    raise ValueError("Размерность должна быть положительным числом ≤ 20")

                # Парсинг точности (вторая строка)
                accuracy = int(content[1])
                if accuracy < 0:
                    raise ValueError("Точность должна быть положительным числом")

                # Проверяем, что достаточно строк для матрицы
                if len(content) < 2 + size:
                    raise ValueError(f"Ожидается {size} строк матрицы после размерности и точности")

                # Парсинг матрицы и вектора b (остальные строки)
                current_matrix.clear()
                b_vector.clear()
                for i in range(size):
                    values = list(map(float, content[i + 2].replace(",", ".").split()))
                    if len(values) != size + 1:
                        raise ValueError(f"Ожидается {size + 1} значений в строке {i + 3}")
                    current_matrix.append(values[:-1])  # Матрица A (без последнего элемента)
                    b_vector.append(values[-1])  # Вектор b (последний элемент)

                # Обновляем интерфейс с уже существующими данными, не инициализируя нулями
                create_matrix_input(size, initialize_zeros=False)
                for i, row in enumerate(matrix_input.controls):
                    for j, field in enumerate(row.controls):
                        field.value = str(current_matrix[i][j]) if j < size else str(b_vector[i])

                # Устанавливаем размерность и точность в поля
                size_input.value = str(size)
                accuracy_input.value = str(accuracy)

                output_text.value = "Данные успешно загружены из файла!"
                page.update()
            except ValueError as ve:
                output_text.value = f"Ошибка при загрузке файла: {ve}"
                page.update()
            except Exception as ex:
                output_text.value = f"Ошибка при загрузке файла: {ex}"
                page.update()
        else:
            output_text.value = "Файл не выбран."
            page.update()

    def solve_matrix(e):
        try:
            if not current_matrix or not b_vector or len(current_matrix) == 0:
                output_text.value = "Сначала введите матрицу и вектор!"
                page.update()
                return
            acc = int(accuracy_input.value) if accuracy_input.value else 2
            result = method_Gauss_Seidel(current_matrix, b_vector, 10 ** (-acc))
            output_text.value = result
            page.update()
        except ValueError as ex:
            output_text.value = f"Ошибка: Введите корректное значение точности! {ex}"
            page.update()

    # Сборка интерфейса
    page.views.append(
        ft.View(
            "/second",
            [
                ft.AppBar(title=ft.Text("Метод Гаусса-Зейделя")),
                ft.Text("Введите размерность матрицы (<= 20):"),
                ft.Row([size_input, ft.ElevatedButton("Обновить", on_click=update_matrix_dimensions)]),
                ft.Text("Введите точность (степень 10):"),
                accuracy_input,
                ft.Text("Введите матрицу и вектор b:"),
                matrix_input,
                ft.Row([
                    ft.ElevatedButton("Загрузить из файла", on_click=lambda _: file_picker.pick_files()),
                    ft.ElevatedButton("Рассчитать", on_click=solve_matrix),
                ]),
                output_text,
                ft.ElevatedButton("Вернуться на главную", on_click=lambda e: page.go("/")),
            ],
            scroll=ft.ScrollMode.AUTO,
        )
    )
    page.overlay.append(file_picker)
    page.update()