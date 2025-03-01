from equations import Eq

set_eq = {
  1: "e^x*x^3 + 0.00001*x^2 + x + 0.125",
  2: "0.000123*x^2 + x + 1",
  3: "sin(e^x)*x^3 + x + 1",
  4: "x^3 - 3.125*x^2 - 3.5*x + 2.458"
}
set_meth = {
  1: "hord",
  2: "secant",
  3: "simple_itter",
  4: "newton"
}


for i in range(1, len(set_eq) + 1):
  print(str(i) + " " + set_eq[i])

user_input = input("Какое уравнение по нраву, мой хозяин?\n")
user_input = user_input.replace(" ", "")
index_eq = int(user_input) - 1

user_input_meth = input("Каким методом хотите данное выражение, мой хозяин?")
user_input_meth = user_input.replace(" ", "")
meth = set_meth[user_input_meth]

expr = set_eq[index_eq]
e = Eq(expr)
# print(e.secant(0.5, 2, 0.01))
# print(user_input)
match user_input_meth:
  case 3:
    
    exec("e." + meth + f"()")
    
    


