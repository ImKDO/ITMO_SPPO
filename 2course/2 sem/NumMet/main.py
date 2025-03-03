import flet as ft

from pages.lab1.lab_1 import show_lab_1
from pages.lab2.views import show_lab_2


def main(page: ft.Page):
    def route_change(route):
        page.views.clear()
        if page.route == "/":
            page.views.append(
                ft.View(
                    "/",
                    [
                        ft.AppBar(title=ft.Text("Главная")),
                        ft.ElevatedButton("Лабораторная 1", on_click=lambda e: page.go("/second")),
                        ft.ElevatedButton("Лабораторная 2", on_click=lambda e: page.go("/third")),
                    ],
                )
            )
        elif page.route == "/second":
            show_lab_1(page)
        elif page.route == "/third":
            show_lab_2(page)
        page.update()

    page.on_route_change = route_change
    page.go(page.route)


if __name__ == "__main__":
    ft.app(main)
