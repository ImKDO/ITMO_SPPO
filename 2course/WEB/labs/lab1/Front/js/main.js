
let x = -5
let canvas = document.getElementById("Canvas")
let ctx = canvas.getContext("2d");
ctx.clearRect(0, 0, canvas.width, canvas.height);


let formX = document.getElementById("x")
formX.querySelectorAll('input[type="button"]').forEach(button => {
    button.addEventListener('click', (event) => {
        x = event.target.value
    })
})



document.getElementById("submitBtn_dot").addEventListener('click', (event) => {
    let y = document.getElementById("textInput").value;
    console.log(!isNaN(y));
    console.log(!isNaN(y));

    if(!isNaN(y) && !(y.trim() === '') && x !== -5) {
        ctx.beginPath();
        ctx.strokeStyle = "yellow"
        ctx.lineWidth = 2;
        ctx.arc(250 + x*50, y*80, 3, 0, 2 * Math.PI);
        ctx.stroke()

    }
})

ctx.beginPath();
ctx.moveTo(250, 200);  // Начинаем рисовать с центра круга
ctx.arc(250, 200, 100, Math.PI,3*Math.PI/2);  // Рисуем дугу от 0 до 90 градусов
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
ctx.stroke()


