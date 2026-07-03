function toggleMenu() {
    const menu = document.getElementById("menuFlutuante");
    menu.style.display = menu.style.display === "block" ? "none" : "block";
}

function abrirConnect() {
    const img = document.getElementById("connectImage");

    img.src = "/qr";

    document.getElementById("connectDialog").style.display = "block";
}

function fecharDialog() {
    document.getElementById("connectDialog").style.display = "none";
    return "/home";
}

function fecharConnect(){
    updateStatusButton('Disconnected');
    window.location.href = "/quit";
}

function start() {
    updateStatusButton('Connected');
    window.location.href = "/start";
}

function tirarPrint(){
    window.location.href = "/print"
}

function updateStatusButton(status) {
    const button = document.getElementById('statusButton');

    if (status === 'Connected') {
        button.classList.add('connected');
        button.classList.remove('disconnected');
    } else {
        button.classList.add('disconnected');
        button.classList.remove('connected');
    }
}