/*setInterval(() => {
    if (document.body.style.background == "white") {
        document.body.style.background = "black";
    } else {
        document.body.style.background = "white";
    }
}, 100);*/

window.onload = function () {
    document.getElementById("index").style.backgroundColor = "red";

    const elements = document.getElementsByClassName("form__input");
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.backgroundColor = "blue";
    }
}