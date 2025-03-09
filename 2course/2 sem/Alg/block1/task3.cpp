#include <cctype>
#include <iostream>
#include <optional>
#include <string>
#include <unordered_map>
#include <vector>

using namespace std;

// Проверяем, что вся строка - целое число (учитывая возможные пробелы в конце)
bool isValidNumber(const string& str) {
  try {
    size_t pos = 0;
    stoi(str, &pos);
    // Убедимся, что после считанного числа остались только пробелы
    while (pos < str.size()) {
      if (!isspace(static_cast<unsigned char>(str[pos]))) {
        return false;  // встретились непробельные символы
      }
      ++pos;
    }
    return true;
  } catch (...) {
    return false;  // stoi выбросил исключение — значит, не число
  }
}

int main() {
  ios::sync_with_stdio(false);  // чтобы ускорить ввод/вывод
  cin.tie(nullptr);

  // Текущее состояние переменных
  unordered_map<string, int> currentScope;

  // Стек изменений. Каждый элемент стека — это вектор изменений:
  // Каждое изменение хранится как (название_переменной, прежнее_значение_или_отсутствовало).
  // Если "прежнего значения" нет (переменная отсутствовала), то хранится std::nullopt.
  vector<vector<pair<string, optional<int>>>> scopeChangesStack;

  string line;
  while (true) {
    if (!getline(cin, line)) {
      // если вход закончился или ошибка чтения
      break;
    }
    if (line.empty()) {
      // пустая строка – выходим, как и в оригинале
      break;
    }

    // Открытие нового блока
    if (line[0] == '{') {
      // Запоминаем, что мы вошли в новый блок: для него создаём
      // отдельный список изменений, пока пустой
      scopeChangesStack.emplace_back();

      // Закрытие блока
    } else if (line[0] == '}') {
      // Откатываем все изменения, сделанные в верхнем блоке
      if (!scopeChangesStack.empty()) {
        auto& changes = scopeChangesStack.back();
        // Идём в обратном порядке, восстанавливая прежние значения
        for (auto it = changes.rbegin(); it != changes.rend(); ++it) {
          const string& varName = it->first;
          const auto& oldValue = it->second;
          if (oldValue.has_value()) {
            // Если в oldValue было число, восстанавливаем его
            currentScope[varName] = oldValue.value();
          } else {
            // Если oldValue == std::nullopt, значит этой переменной не было
            currentScope.erase(varName);
          }
        }
        // Удаляем вектор изменений верхнего блока
        scopeChangesStack.pop_back();
      }

    } else {
      // Ищем символ '=' (присваивание)
      auto pos = line.find('=');
      if (pos != string::npos) {
        // Левая часть (до '=') — имя переменной
        string left = line.substr(0, pos);
        // Правая часть (после '=') — значение или другая переменная
        string right = line.substr(pos + 1);

        // Проверяем, число ли это
        if (isValidNumber(right)) {
          // Если число, сразу конвертируем
          int value = stoi(right);

          // Сохраняем "старое значение" (если оно было) в список изменений
          if (!scopeChangesStack.empty()) {
            auto& changes = scopeChangesStack.back();
            // Проверяем, существовала ли переменная
            auto it = currentScope.find(left);
            if (it == currentScope.end()) {
              // Не существовала: пишем (left, nullopt)
              changes.push_back({left, std::nullopt});
            } else {
              // Существовала: пишем (left, старое_значение)
              changes.push_back({left, it->second});
            }
          }
          // Обновляем значение в текущей области видимости
          currentScope[left] = value;

        } else {
          // Иначе предположим, что это имя другой переменной
          // (как в исходном коде: !isdigit(str[2])) – проверка довольно сомнительная,
          // но сохраним логику исходника.
          // В реальных задачах лучше делать полноценные проверки.
          if (line.size() > 2 && !isdigit(line[2])) {
            // Сохраняем старое значение в "изменениях"
            if (!scopeChangesStack.empty()) {
              auto& changes = scopeChangesStack.back();
              auto it = currentScope.find(left);
              if (it == currentScope.end()) {
                changes.push_back({left, std::nullopt});
              } else {
                changes.push_back({left, it->second});
              }
            }
            // Присваиваем
            currentScope[left] = currentScope[right];
            cout << currentScope[left] << "\n";
          }
        }
      }
    }
  }

  return 0;
}
