import flet as ft
from flet.core.dropdownm2 import Option

def create_main_dropdown():
    return ft.Dropdown(
        width=200,
        hint_text="Сьта виебирать моя хозяина?",
        options=[
            ft.dropdown.Option("Сьисьтемьа НУ"),
            ft.dropdown.Option("НУ"),
        ],
    )


def create_eq_container():
    return ft.Container(content=None)  # Явно указываем, что контент пуст


def create_equation_dropdown():
    return ft.Dropdown(
        width=200,
        hint_text="Выберите уравнение",
        options=[
            ft.dropdown.Option("x^3 - 3.125*x^2 - 3.5*x + 2.458"),
            ft.dropdown.Option("sin(e^x)*x^3 + x + 1"),
            ft.dropdown.Option("0.000123*x^2 + x + 1"),
            ft.dropdown.Option("e^x*x^3 + 0.00001*x^2 + x + 0.125"),
        ],
    )

def create_system_equation_dropdown():
    return ft.Dropdown(
        width=200,
        hint_text="Выберите систему уравнений",
        options=[
            ft.dropdown.Option("------------\nx^3 - 3.125*x^2 - 3.5*x + 2.458 \n |-------| \n sin(e^x)*x^3 + x + 1\n------------"),
            ft.dropdown.Option("------------\nx^3 - 3.125*x^2 - 3.5*x + 2.458 \n |-------| \n sin(e^x)*x^3 + x + 1\n------------"),
            ft.dropdown.Option("------------\nx^3 - 3.125*x^2 - 3.5*x + 2.458 \n |-------| \n sin(e^x)*x^3 + x + 1\n------------"),
        ]
    )

def create_default_dropdown():
    return ft.Dropdown(
        width=200,
        hint_text="Сьта виебирать моя хозяина?",
        options=[
            ft.dropdown.Option("Сьисьтемьа НУ"),
            ft.dropdown.Option("НУ"),
        ],
    )
