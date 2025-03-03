import flet as ft
from .components import create_equation_dropdown, create_system_equation_dropdown

def check_pick_view(e, dropdown, container, page):
    selected_value = dropdown.value
    if selected_value == "Сьисьтемьа НУ":
        container.content = create_system_equation_dropdown()
    else:
        container.content = create_equation_dropdown()
    page.update()
