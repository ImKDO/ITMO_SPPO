package web.lab3

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Named

@Named
@RequestScoped
class InputData {
    var x: Int = 0
    var y: Double = 0.0
    var r: Double = 0.0

    fun getParams(x: Int, y: Double, r: Double): String {
        // Формируем строку для вывода
        println("Получены данные: X=$x, Y=$y, R=$r")
        return "X: $x, Y: $y, R: $r"
    }

    fun processData() {
        // Логируем значения для проверки
        println("Получены данные: X=$x, Y=$y, R=$r")
    }
}
