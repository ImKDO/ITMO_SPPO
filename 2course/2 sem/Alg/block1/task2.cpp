#include <strings.h>

#include <algorithm>
#include <cctype>
#include <iostream>
#include <vector>
using namespace std;

bool find_element(const vector<char>& v, char ch) {
  for (char i : v) {
    if (i == ch)
      return true;
  }
  return false;
}

int main() {
  string str;
  cin >> str;
  vector<char> upper_letters;
  vector<char> lower_letters;

  for (char ch = 'a'; ch <= 'z'; ++ch) {
    lower_letters.push_back(ch);
  }
  for (char ch = 'A'; ch <= 'Z'; ++ch) {
    upper_letters.push_back(ch);
  }

  vector<char> stack_animals;
  vector<char> stack_traps;
  int n = str.length();
  int n_half = n / 2;

  vector<int> result;

  for (int i = 0; i < n_half; ++i) {
    if (find_element(upper_letters, str[i])) {
      stack_traps.push_back(str[i]);
    } else {
      stack_animals.push_back(str[i]);
    }

    if (find_element(lower_letters, str[n - i - 1])) {
      stack_animals.push_back(str[n - i - 1]);
    } else {
      stack_traps.push_back(str[n - i - 1]);
    }

    if (!stack_animals.empty() && !stack_traps.empty() &&
        toupper(stack_animals.back()) == stack_traps.back()) {
      stack_animals.pop_back();
      stack_traps.pop_back();
      result.push_back(i + 1);
        }
  }

  if (!stack_animals.empty() || !stack_traps.empty()) {
    cout << "Impossible" << endl;
  } else {
    cout << "Possible" << endl;
    for (int i = result.size() - 1; i >= 0; --i) {
      cout << result[i] << " ";
    }
    cout << endl;
  }

  return 0;
}