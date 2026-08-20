package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.entity.Alert;
import com.accesses.administrative_system.entity.enums.AlertType;
import com.accesses.administrative_system.message_api.config.SeleniumManager;
import com.accesses.administrative_system.message_api.utils.QrImageTreatment;
import com.accesses.administrative_system.message_api.v2.constants.SeleniumConstants;
import com.accesses.administrative_system.message_api.v2.models.Message;
import com.accesses.administrative_system.usecases.EmitAlert;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class MessageServiceV2 {

    private final SendMessage sendMessage;
    private final SeleniumManager seleniumManager;
    private final GetAuthentication getAuthentication;
    private final QrImageTreatment qrImageTreatment;
    public final EmitAlert emitAlert;

    public MessageServiceV2(SendMessage sendMessage, SeleniumManager seleniumManager, GetAuthentication getAuthentication, QrImageTreatment qrImageTreatment, EmitAlert emitAlert) {
        this.sendMessage = sendMessage;
        this.seleniumManager = seleniumManager;
        this.getAuthentication = getAuthentication;
        this.qrImageTreatment = qrImageTreatment;
        this.emitAlert = emitAlert;
    }

    public void connect(){
        emitAlert.execute(Alert.builder()
                .message("Tentando se conectar ao Drive")
                .alertType(AlertType.TRY_CONNECT_DRIVE)
                .build());

        seleniumManager.connect();

        emitAlert.execute(Alert.builder()
                .message("Conectado ao Drive com sucesso")
                .alertType(AlertType.CONNECT_DRIVER_SUCCESSFUL)
                .build());
    }

    public void disconnect(){
        emitAlert.execute(Alert.builder()
                .message("Tentando se disconectar do Drive")
                .alertType(AlertType.TRY_DISCONNECT_DRIVE)
                .build());

        seleniumManager.disconnect();

        emitAlert.execute(Alert.builder()
                .message("Disconectado do Drive com sucesso")
                .alertType(AlertType.DISCONNECT_DRIVER_SUCCESSFUL)
                .build());
    }

    public void sendWithStudentClass(){

    }

    public void sendWithSimpleParam(Message message){

        emitAlert.execute(Alert.builder()
                .message("Tentando mandar mensagem para o(s) numero(s): %s"
                        .formatted(message.getPhoneNumber()))
                .alertType(AlertType.TRY_SEND_MESSAGE)
                .data(message)
                .build());

        sendMessage.execute(message);

    }

    public byte[] getQrCodeForAuth() throws IOException {

        emitAlert.execute(Alert.builder()
                .message("Tentando pegar QR para login")
                .alertType(AlertType.TRY_LOGIN_WHATSAPP)
                .build());

        getAuthentication.execute();

        emitAlert.execute(Alert.builder()
                .message("Tentativa para pegar QR realizada com sucesso")
                .alertType(AlertType.LOGIN_WHATSAPP_SUCCESSFUL)
                .build());

        return qrImageTreatment.getImageAsBytes(SeleniumConstants.QR_CODE_PATH);

    }

    public void closePopUp(){

        WebDriver webDriver = seleniumManager.getWebDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        String whatsIsNewPopUp = "//button[.//*[name()='title' and text()='ic-close']]";

        List<WebElement> popupElements;

        try {
            popupElements = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(whatsIsNewPopUp))
            );
        }catch (Exception e){
            popupElements = new ArrayList<>();
        }

        if (!popupElements.isEmpty()) {
            popupElements.getFirst().click();
            log.info("Popup 'O que há de novo' fechado");
        } else {
            log.info("Popup não encontrado, seguindo em frente");
        }
    }

}
