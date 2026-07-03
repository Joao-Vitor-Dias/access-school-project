package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.message_api.config.SeleniumManager;
import com.accesses.administrative_system.message_api.utils.QrImageTreatment;
import com.accesses.administrative_system.message_api.v2.constants.SeleniumConstants;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
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


    public MessageServiceV2(SendMessage sendMessage, SeleniumManager seleniumManager, GetAuthentication getAuthentication, QrImageTreatment qrImageTreatment) {
        this.sendMessage = sendMessage;
        this.seleniumManager = seleniumManager;
        this.getAuthentication = getAuthentication;
        this.qrImageTreatment = qrImageTreatment;
    }

    public void connect(){
        seleniumManager.connect();
    }

    public void disconnect(){
        seleniumManager.disconnect();
    }

    public void sendWithStudentClass(){

    }

    public void sendWithSimpleParam(Message message){

        sendMessage.execute(message);

    }

    public byte[] getQrCodeForAuth() throws IOException {

        getAuthentication.execute();

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
