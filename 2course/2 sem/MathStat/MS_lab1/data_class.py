class Sepal:
    def __init__(self,Length,Width):
        self.Length = Length
        self.Width = Width
    def __str__(self):
        return (f"Sepal({self.Length}, {self.Width})")
    
class Petal:
     def __init__(self,Length,Width):
        self.Length = Length
        self.Width = Width
     def __str__(self):
        return (f"Petal({self.Length}, {self.Width})")

class Attributes:
    def __init__(self,Sepal,Petal, Species):
        self.Sepal = Sepal
        self.Petal = Petal
        self.Species = Species
    def __str__(self):
        return (f"Data({self.Sepal}, {self.Petal}, Species({self.Species}))")