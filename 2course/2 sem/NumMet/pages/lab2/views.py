import flet as ft
from .components import create_main_dropdown, create_eq_container, create_equation_dropdown, create_default_dropdown
from .handlers import check_pick_view

def show_lab_2(page: ft.Page):
    page.title = "НиСНУ"
    page.vertical_alignment = ft.MainAxisAlignment.CENTER

    # Первый выпадающий список
    list_pick_view_eq = create_main_dropdown()

    # Второй выпадающий список (контейнер с начальным значением)
    list_eq_container = create_eq_container()

    # Привязываем обработчик к первому списку
    list_pick_view_eq.on_change = lambda e: check_pick_view(
        e, list_pick_view_eq, list_eq_container, page)

    # Создаём View с содержимым
    view = ft.View(
        "/third",
        [
            ft.AppBar(title=ft.Text("2 лабораторная")),
            ft.Text("Решение НсСНУ"),
            list_pick_view_eq,
            list_eq_container,
            ft.ElevatedButton(
                "Вернуться на главную страницу",
                on_click=lambda e: page.go("/")
            ),
        ],
        vertical_alignment=ft.MainAxisAlignment.CENTER,
        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
    )

    # Очищаем и добавляем View
    page.views.clear()
    page.views.append(view)
    page.update()
