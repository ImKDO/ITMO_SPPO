let x = 60;
let y = 0;
let r = 60

let canvas = document.getElementById("Canvas")
let ctx = canvas.getContext("2d");
ctx.clearRect(0, 0, canvas.width, canvas.height);


//--------------------------------------------------------
// Функция для добавления новой записи в таблицу
function addResultRow(x, y, r, hit, timeScript, nowTime) {
    const resultTableBody = document.getElementById('resultTableBody');

    // Создаем новый элемент строки таблицы
    const row = document.createElement('tr');

    // Добавляем значения в ячейки
    row.innerHTML = `
        <td>${x}</td>
        <td>${y}</td>
        <td>${r}</td>
        <td>${hit}</td>
        <td>${timeScript} нс</td>
        <td>${nowTime}</td>
    `;

    // Добавляем новую строку в тело таблицы
    resultTableBody.appendChild(row);
}

//--------------------------------------------------------

//Функция fetch запроса с ответом
//--------------------------------------------------------
function fetchRequest(x,y,r){
    // Формируем строку параметров для GET-запроса
    const params = new URLSearchParams({
        x: x,  // Параметр 'x' со значением переменной x
        y: y,   // Параметр 'y' со значением переменной y   
        r: r
    }).toString();




    fetch(`/fcgi-bin/?x=${x}&y=${y}&r=${r}`, {
        method: 'GET'
    })

        .then(response => {
            console.log(response)
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.text();
        })

        .then(answer => {
            let resultParse = JSON.parse(answer);
            const xRes = resultParse.x
            const yRes = resultParse.y
            const rRes = resultParse.r
            const result = resultParse.result
            const scrTime = resultParse.scrTime
            const currentTime = resultParse.currTime
            const error = resultParse.error
            addResultRow(xRes,yRes,rRes,result,scrTime, currentTime)

        })
}



//--------------------------------------------------------


//Получаем значение x
//--------------------------------------------------------
let formX = document.getElementById("x")
formX.querySelectorAll('input[type="button"]').forEach(button => {
    button.addEventListener('click', (event) => {
        x = event.target.value
    })
})
//Получаем значение r
let formR = document.getElementById("radius")
formR.querySelectorAll('input[type="radio"]').forEach(button => {
    button.addEventListener('click', (event) => {
        r = event.target.value
    })
})
//--------------------------------------------------------

//--------------------------------------------------------
document.getElementById("submitBtn_dot").addEventListener('click', (event) => {
    y = document.getElementById("textInput").value;

    if (!isNaN(y) && !(y.trim() === '') && x !== 60 && r != 60) {
        ctx.beginPath();
        ctx.strokeStyle = "yellow";
        ctx.lineWidth = 2;
        ctx.arc(250 + x * 50, 200 - y * 33, 3, 0, 2 * Math.PI);
        ctx.stroke()
    } else{
        alert("Введите валидные")
    }
})
//--------------------------------------------------------
document.getElementById("submitBtn").addEventListener('click', (event) => {
    fetchRequest(x,y,r)
})




// Отрисовка картинки
ctx.beginPath();
ctx.moveTo(250, 200);  // Начинаем рисовать с центра круга
ctx.arc(250, 200, 100, Math.PI, 3 * Math.PI / 2);  // Рисуем дугу от 0 до 90 градусов
ctx.lineTo(250, 200);  // Замыкаем сектор линией обратно к центру
ctx.closePath();

ctx.fillStyle = "blue";  // Устанавливаем цвет заливки
ctx.fill();  // Закрашиваем сектор
ctx.strokeStyle = "black";  // Устанавливаем цвет обводки

// ctx.beginPath();  // Начинаем новый путь
ctx.moveTo(250, 200);  // Перемещаемся к первой вершине треугольника
ctx.lineTo(250, 310);   // Линия ко второй вершине
ctx.lineTo(150, 200);  // Линия к третьей вершине
ctx.closePath();  // Замыкаем путь (соединяет последнюю точку с первой)

ctx.fillStyle = "blue";  // Устанавливаем цвет заливки
ctx.fill();  // Закрашиваем треугольник
ctx.strokeStyle = "black";  // Устанавливаем цвет обводки

// ctx.fillStyle = "green";  // Устанавливаем цвет заливки
ctx.fillRect(250, 200, 100, 110);  // Рисуем прямоугольник с координатами (50, 50), шириной 100 и высотой 150


ctx.stroke();  // Рисуем обводку


ctx.stroke();  // Рисуем обводку

// Рисуем закрашенный прямоугольник
ctx.beginPath();


// Настройка стиля

// Нарисуем оси X и Y
ctx.beginPath();
ctx.strokeStyle = "black"; // цвет линии
ctx.lineWidth = 2;        // толщина линии


ctx.moveTo(250, 0); // Начало линии
ctx.lineTo(250, 500); // Ось Y11

ctx.moveTo(0, 200); // Начало линии
ctx.lineTo(500, 200); // Ось X

ctx.stroke();

ctx.beginPath();
ctx.strokeStyle = "red"; // цвет линии

ctx.font = '16px Arial'; // Устанавливаем шрифт и размер
ctx.fillStyle = 'red'; // Устанавливаем цвет текста
ctx.fillText('R/2', 280, 155); // Рисуем текст рядом с линией

// Рисуем деление для оси

//Вертикальныая ось ----------
ctx.moveTo(240, 150);
ctx.lineTo(260, 150);

ctx.moveTo(240, 250);
ctx.lineTo(260, 250);

ctx.moveTo(200, 190);
ctx.lineTo(200, 210);

ctx.moveTo(300, 190);
ctx.lineTo(300, 210);
//Вертикальныая ось ----------

//Горизонтальная ось ---------
ctx.moveTo(240, 100);
ctx.lineTo(260, 100);

ctx.moveTo(240, 310);
ctx.lineTo(260, 310);

ctx.moveTo(150, 190);
ctx.lineTo(150, 210);

ctx.moveTo(350, 190);
ctx.lineTo(350, 210);
//Горизонтальная ось ---------
ctx.stroke();