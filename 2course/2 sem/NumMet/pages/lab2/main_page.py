import flet as ft

def main(page: ft.Page):
    # Настройки страницы
    page.title = "Мое первое Flet-приложение"
    page.vertical_alignment = ft.MainAxisAlignment.CENTER
    page.horizontal_alignment = ft.CrossAxisAlignment.CENTER
    page.padding = 50

    # Создаем текстовое поле с приветствием
    hello_text = ft.Text(
        value="Привет, Flet!",
        size=40,
        color="blue",
        weight="bold",
    )

    # Добавляем текст на страницу
    page.add(hello_text)

# Запуск приложения
ft.app(target=main, view=ft.WEB_BROWSER)