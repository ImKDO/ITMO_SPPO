from matplotlib import pyplot as plt
from read_data import read_data


def emp_fn():
    data = read_data()
    data_sq_petal = [e.Petal.Length*e.Petal.Width for e in data]
    data_sq_petal.sort()

    flag = data_sq_petal[0]
    k = 0
    n = len(data_sq_petal)
    for i in range(n):
        if flag != data_sq_petal[i]:
            flag = data_sq_petal[i]
            plt.plot(k/n, i)
        else:
            k += 1
    plt.show()
