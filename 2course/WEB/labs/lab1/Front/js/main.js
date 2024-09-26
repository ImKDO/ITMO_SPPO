let x = 0

let formX = document.getElementById("x")
formX.querySelectorAll('input[type="button"]').forEach(button => {
    button.addEventListener('click', (event) => {
        x = event.target.value
        console.log(`${x}`)
    })
})

let formY = document.getElementById("y")



