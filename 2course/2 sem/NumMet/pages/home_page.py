import flet as ft

def show_home_page(page: ft.Page):
    # Очищаем страницу и добавляем контент
    page.views.append(
        ft.View(
            "/",
            [
                ft.AppBar(title=ft.Text("Главная страница")),
                ft.ElevatedButton("Решение СЛАУ", on_click=lambda e: page.go("/second")),
                ft.ElevatedButton("Решение НиСНУ", on_click=lambda e: page.go("/third")),
            ],
        )
    )
    page.update()