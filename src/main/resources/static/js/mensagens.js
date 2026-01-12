function toggleMenu() {
    const menu = document.getElementById("menuFlutuante");
    menu.style.display = menu.style.display === "block" ? "none" : "block";
}

function abrirConnect() {
    const img = document.getElementById("connectImage");

    // endpoint que retorna a imagem
    img.src = "/qr";

    document.getElementById("connectDialog").style.display = "block";
}

function fecharDialog() {
    document.getElementById("connectDialog").style.display = "none";
    return "/home";
}

function fecharConnect(){
    window.location.href = "/quit";
}

function start() {
    window.location.href = "/start";
}
