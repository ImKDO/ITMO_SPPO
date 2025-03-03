import flet as ft

def show_second_page(page: ft.Page):
    # Очищаем страницу и добавляем контент для второй страницы
    page.views.append(
        ft.View(
            "/second",
            [
                ft.AppBar(title=ft.Text("Вторая страница")),
                ft.Text("Это вторая страница!"),
                ft.ElevatedButton("Вернуться на главную страницу", on_click=lambda e: page.go("/")),
            ],
        )
    )
    page.update()