const driverStatus = document.getElementById("driverStatus");
const whatsappStatus = document.getElementById("whatsappStatus");

const btnConnect = document.getElementById("btnConnect");
const btnDisconnect = document.getElementById("btnDisconnect");
const btnPopup = document.getElementById("btnPopup");
const btnSend = document.getElementById("btnSend");
const btnRefreshQr = document.getElementById("btnRefreshQr");

const phone = document.getElementById("phone");
const message = document.getElementById("message");

const qr = document.getElementById("qrImage");

const log = document.getElementById("log");


function addLog(alert) {

    const div = document.createElement("div");

    div.classList.add("log-item");

    switch (alert.alertType){

        case "MESSAGE_SEND_SUCCESSFUL":
        case "CONNECT_DRIVER_SUCCESSFUL":
        case "LOGIN_WHATSAPP_SUCCESSFUL":
            div.classList.add("log-success");
            break;

        case "MESSAGE_SEND_FAILURE":
        case "CONNECT_DRIVER_FAILURE":
        case "LOGIN_WHATSAPP_FAILURE":
            div.classList.add("log-error");
            break;

        default:
            div.classList.add("log-info");
    }

    div.innerHTML =
        "[" + alert.timestamp + "] "
        + alert.message;

    log.prepend(div);

}


const eventSource =
    new EventSource("/notifications/subscribe");

eventSource.onmessage = function(event){

    const alert = JSON.parse(event.data);

    console.log(alert);

    addLog(alert);

    updateStatus(alert);

};

function updateStatus(alert){

    switch(alert.alertType){

        case "CONNECT_DRIVER_SUCCESSFUL":

            driverStatus.className = "badge bg-success";
            driverStatus.innerHTML = "Selenium ON";

            break;

        case "DISCONNECT_DRIVER_SUCCESSFUL":

            driverStatus.className = "badge bg-danger";
            driverStatus.innerHTML = "Selenium OFF";

            whatsappStatus.className = "badge bg-danger";
            whatsappStatus.innerHTML = "WhatsApp OFF";

            break;

        case "LOGIN_WHATSAPP_SUCCESSFUL":

            whatsappStatus.className = "badge bg-success";
            whatsappStatus.innerHTML = "WhatsApp ON";

            break;

        case "TRY_LOGIN_WHATSAPP":

            whatsappStatus.className = "badge bg-warning";
            whatsappStatus.innerHTML = "Aguardando QR Code";

            refreshQr();

            break;

        case "TRY_WHATSAPP_OPEN_WINDOW":

            refreshQr();

            break;

    }

}

function refreshQr(){

    qr.src =
        "/v2/message/qr?t=" + new Date().getTime();

}

btnConnect.onclick = async () => {

    await fetch("/v2/message/connect");

};

btnDisconnect.onclick = async () => {

    await fetch("/v2/message/disconnect");

};

btnPopup.onclick = async () => {

    await fetch("/v2/message/close/popup");

};

btnRefreshQr.onclick = refreshQr;

btnSend.onclick = async () => {

    const body = {

        phoneNumber: phone.value,

        principal: message.value

    };

    await fetch("/v2/message/send",{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(body)

    });

};

eventSource.onerror = function(){

    addLog({

        timestamp:new Date().toLocaleTimeString(),

        message:"Conexão SSE perdida.",

        alertType:"ERROR"

    });

};


window.onbeforeunload = () => {

    eventSource.close();

}

