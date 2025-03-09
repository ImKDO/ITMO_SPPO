#include <iostream>
using namespace std;

int main() {
  int a, b, c, d;
  long k;
  long prev = 0;
  cin >> a >> b >> c >> d >> k;
  for (int i = 0; i < k; ++i) {
    if (prev == a)
      break;
    prev = a;
    a = a * b;
    if (a >= c) {
      a = a - c;
    } else {
      a = 0;
      break;
    }
    if (a > d) {
      if (d == 0)
        break;
      a = d;
    }
  }
  cout << a;
}