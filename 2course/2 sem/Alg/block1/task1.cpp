#include <iostream>
#include <vector>

using namespace std;

int main() {
  int n;
  cin >> n;
  vector<long> v(n);
  for (int i = 0; i < n; i++) {
    cin >> v[i];
  }

  int left = 0;        // Левый указатель
  int max_len = 1;     // Максимальная длина участка
  int best_left = 0;   // Левый индекс лучшего участка
  int best_right = 0;  // Правый индекс лучшего участка

  for (int right = 1; right < n; ++right) {
    // Проверяем, есть ли три одинаковых цветка подряд
    if (right >= 2 && v[right] == v[right - 1] && v[right] == v[right - 2]) {
      // Если есть, сдвигаем левый указатель
      left = right - 1;
    }

    // Обновляем максимальную длину и индексы лучшего участка
    if (right - left + 1 > max_len) {
      max_len = right - left + 1;
      best_left = left;
      best_right = right;
    }
  }

  // Выводим результат (нумерация с 1)
  cout << best_left + 1 << " " << best_right + 1 << endl;

  return 0;
}