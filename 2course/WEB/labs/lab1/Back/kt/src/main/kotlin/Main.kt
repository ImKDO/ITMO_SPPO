import com.fastcgi.FCGIInterface

import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureNanoTime


fun main() {

    var beginHTMLresponse = """<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>lab1</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="imgs/title.svg">
</head>


<body>
<header>
    <div class="flp header_blocks_class">
        <p>Кравченко</p>
        <p>Дмитрий</p>
        <p>Олегович</p>
    </div>
    <div class="group header_blocks_class">
        <p>Группа: P3219</p>
    </div>
    <div class="lab_number header_blocks_class">
        <p>Вариант: 408891</p>
    </div>
</header>
<img src="imgs/asd.gif" style="width: 25%;">
<p>Введите данные:</p>

<main>
    <div></div>
    <div class="form_for_img">
        <div class="check_dot grid_form_layout">
            <div id="x" class="check_dot_block_X">
                X:
                <form class="check_dot_blocks">
                    <input class="styleInput" type="button" value="-2" lang="X_button">
                    <input class="styleInput" type="button" value="-1" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">
                    <input class="styleInput" type="button" value="0" lang="X_button">

                </form>
            </div>
            <div id="y">
                <form class="check_dot_blocks styleInput">
                    Y:
                    <input class="styleInput" id="textInput" maxlength="5" type="text">
                </form>
            </div>
            <div class="styleInput">
                <button id="submitBtn_dot" class="check_dot_blocks styleInput">Проверить точку</button>
            </div>

        </div>
        <div class="styleInput">
            <form id="radius">
                R:
                <input type="radio" name="radius" value="10">1
                <input type="radio" name="radius" value="5">2
                <input type="radio" name="radius" value="24">3
            </form>
            <button onclick="drawGraph()" id="submitBtn" class="styleInput">Отправить данные</button>
        </div>
        <div id="draw_img">
            <canvas id="Canvas" width="500" height="400"></canvas>
        </div>
    </div>
    <div class="resTable">
        <div class="resTable_blocks">x</div>
        <div class="resTable_blocks">y</div>
        <div class="resTable_blocks">R</div>
        <div class="resTable_blocks">Попал?</div>
        <div class="resTable_blocks">Время выполнения скрипта</div>
        <div >Когда был выполнен скрипт</div>
    </div>
</main>
</body>
<script src="js/main.js"></script>
</html>"""

    var fcgiInterface = FCGIInterface()
    while (fcgiInterface.FCGIaccept() >= 0) {

        // Получаем метод запроса
        val requestMethod = FCGIInterface.request.params.getProperty("REQUEST_METHOD")

        // Проверяем, что это GET-запрос
        if (requestMethod == "GET") {
            // Получаем параметры из строки запроса
            val queryString = FCGIInterface.request.params.getProperty("QUERY_STRING")
            // Преобразуем в объект Date
            val currentTimeMillis = System.currentTimeMillis()

            val date = Date(currentTimeMillis)

            // Форматируем дату в читаемый формат
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            var content = """
            {
            "status":"%s",
            "x":"%d",
            "y":"%f",
            "r":"%f",
            "hit":"%b",
            "time_script":"%d",
            "time_now":"$formattedDate"
            }
        """.trimMargin()

            // Обработка параметров
            val params = queryString?.split("&")?.associate {
                val (key, value) = it.split("=")
                key to value
            }
            val x = params?.get("x")
            val y = params?.get("y")
            val r = params?.get("r")
            if (validateValuesXYR(x, y, r)) {
                val xNum = x?.toInt()
                val yNum = y?.toDouble()
                val rNum = r?.toDouble()
                val timeScript = measureNanoTime {
                    script(xNum!!, yNum!!, rNum!!)
                }
//                    System.err.println(script(xNum!!, yNum!!, rNum!!))
                if (script(xNum!!, yNum!!, rNum!!)) {
                    // Обновляем переменную content
                   content = content.format("true",xNum,yNum,rNum,true,timeScript)
                } else{
                    content = content.format("false",xNum,yNum,rNum,false,timeScript)
                }
            } else{
                content = content.format()
            }


            var httpResponse = """
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: %d

%s""".format("content".toByteArray(StandardCharsets.UTF_8).toString().length, content)
            println(httpResponse)
        }
    }
}


fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

fun validateValuesXYR(x: String?, y: String?, r: String?): Boolean {
    return isNumeric(x.toString()) && isNumeric(y.toString()) && isNumeric(r.toString())
}

fun script(x: Int, y: Double, r: Double): Boolean {
    val areaCircle = ((x * x + y * y) <= r * r) && (x <= 0) && (y >= 0)
    val areaTriangle = (x <= 0) && (y <= 0) && (y >= -x - r)
    val areaReactangle = (y <= 0) && y <= (-r) && (x >= 0) && (x <= r / 2)
    return areaCircle || areaTriangle || areaReactangle
}