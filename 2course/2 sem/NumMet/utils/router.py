class Router:
    def __init__(self, page):
        self.page = page
        self.routes = {}
        self.page.on_route_change = self.route_change

    def register(self, route, view_function):
        """Регистрирует новую страницу."""
        self.routes[route] = view_function

    def go(self, route):
        """Переходит на указанный маршрут."""
        self.page.route = route
        self.route_change(None)

    def route_change(self, e):
        """Обрабатывает изменение маршрута."""
        self.page.views.clear()
        view_function = self.routes.get(self.page.route, self.routes["/"])
        view_function(self.page)
        self.page.update()