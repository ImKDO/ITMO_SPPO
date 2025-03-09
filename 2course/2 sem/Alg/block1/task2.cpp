#include <iostream>
#include <stack>
#include <unordered_map>
#include <vector>
using namespace std;

int main() {
  string s;
  cin >> s;

  stack<int> animalStack;
  vector<int> result(s.size() / 2);
  unordered_map<char, int> trapToIndex;

  for (size_t i = 0; i < s.size(); ++i) {
    if (islower(s[i])) {
      animalStack.push(i);
    } else {
      if (animalStack.empty()) {
        cout << "Impossible" << endl;
        return 0;
      }
      if (islower(s[animalStack.top()])) {
        cout << animalStack.top() + 1 << endl;
        result[trapToIndex.size()] = animalStack.top() + 1;
        trapToIndex[s[i]] = trapToIndex.size();
        animalStack.pop();
      } else {
        cout << "Impossible" << endl;
        return 0;
      }
    }
  }

  if (!animalStack.empty()) {
    cout << "Impossible" << endl;
    return 0;
  }

  cout << "Possible" << endl;
  for (int index : result) {
    cout << index << " ";
  }
  cout << endl;
  return 0;
}