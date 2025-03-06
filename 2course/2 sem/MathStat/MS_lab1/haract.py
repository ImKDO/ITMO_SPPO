from data_class import *
from read_data import read_data

def find_harac():
  data_oo = read_data()
  data_flowers = []
  for e in data_oo:
      if e.Species not in data_flowers:
          data_flowers.append(e.Species)
  data_flowers_dict = {}
  for i in range(len(data_flowers)):
      data_flowers_dict[data_flowers[i]] = i

  #---------------------------------------------------------

  def find_M_D_Q(data):
      data_M = sum(data)/len(data)
      data_D = sum(list(map(lambda x: x**2, data)))/len(data) - data_M**2
      print("-----------------------------------")
      print(f"Выборочное среднее: {data_M}")
      print(f"Выборочная дисперсия:{data_D}")
      print(f"Квантиль порядка 2/5: {(2/5)*len(data_oo)}") 
      print("-----------------------------------")

  def find_W_H_M_D_Q(flag, flower):
      if flag == True:
          # Суммарная площадь чашелистика
          print("Чашелистик:")
          full_array_sepal = [e.Sepal.Width*e.Sepal.Length  for e in data_oo if e.Species==flower]
          find_M_D_Q(full_array_sepal)

          # Суммарная площадь лепестка
          print("Лепесток:")
          full_array_petal = [e.Petal.Width*e.Petal.Length  for e in data_oo if e.Species==flower]
          find_M_D_Q(full_array_petal)

          # Совокупность площадт пепестка и чашелистика
          print("Чашелистик + Лепесток:")
          full_array_petal_sepal = full_array_petal + full_array_sepal
          find_M_D_Q(full_array_petal_sepal)

          print("Чашелистик + Лепесток:")
          full_array_petal_sepal = full_array_petal + full_array_sepal
          find_M_D_Q(full_array_petal_sepal)
      else:
          # Суммарная площадь чашелистика
          print("Чашелистик:")
          full_array_sepal = [e.Sepal.Width*e.Sepal.Length  for e in data_oo]
          find_M_D_Q(full_array_sepal)

          # Суммарная площадь лепестка
          print("Лепесток:")
          full_array_petal = [e.Petal.Width*e.Petal.Length  for e in data_oo]
          find_M_D_Q(full_array_petal)

          # Совокупность площадт пепестка и чашелистика
          print("Чашелистик + Лепесток")
          full_array_petal_sepal = full_array_petal + full_array_sepal
          find_M_D_Q(full_array_petal_sepal)

  for f in data_flowers:
      print(f)
      find_W_H_M_D_Q(True, f)
      print("#################################################################\n")

  print("\n#################################################################")
  print("Для всех цветов:")
  find_W_H_M_D_Q(False, "")
  print("#################################################################\n")

  count_flowers = [0]*len(data_flowers)

  for e in data_oo:
      count_flowers[data_flowers_dict[e.Species]] += 1
  data_flowers = [i.replace("\"", "") for i in data_flowers]

  print("-----------------------------------")
  for i in range(len(data_flowers)):
      print(f"{data_flowers[i]}: {count_flowers[i]}")

  def M(count):
      return sum(count)/len(count)

  print(f"\nВыборочное среднее:{M(count_flowers)}")
  print((sum(count_flowers)/3)**2)
  print(M(count_flowers)**2)
  print(f"Дисперсия: {M(list(map( lambda x: x**2,count_flowers))) - M(count_flowers)**2}")
  print(f"Медиана: {data_oo[len(data_oo)//2].Species}")

  print("-----------------------------------")