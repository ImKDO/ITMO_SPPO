from data_class import *

def read_data():
  file = open("./iris.csv", "r")
  data = file.read()
  data_array_view = data.splitlines()
  data_view = []
  file.close()
  for line in data_array_view:
      new_data = []
      line = line.split(",")
      for j in range(len(line)):
          if j == len(line) - 1:
              break
          new_data.append(float(line[j]))
      new_data.append(line[len(line) - 1])
      data_view.append(new_data)
  data_oo = [Attributes(Sepal(e[0], e[1]), Petal(e[2], e[3]), e[4]) for e in data_view]
    
  return data_oo