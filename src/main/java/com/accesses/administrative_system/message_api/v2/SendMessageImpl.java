package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.message_api.config.SeleniumManager;
import com.accesses.administrative_system.util.TelephoneFormatter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Log4j2
public class SendMessageImpl implements SendMessage{

    private final SeleniumManager seleniumManager;

    public SendMessageImpl(SeleniumManager seleniumManager) {
        this.seleniumManager = seleniumManager;
    }

    @Override
    public void execute(Message message) {

        if (message.principal == null){
            throw new RuntimeException("Message can`t be null");
        }

        if (message.phoneNumber == null){
            throw new RuntimeException("Message can`t be null");
        }

        List<String> studentsNumbers = new ArrayList<>();

        // If number input(from controller) have 55 change to 13 and change phone formatter
        if (message.phoneNumber.length() != 11){

            studentsNumbers = Arrays.stream(message.phoneNumber.trim()
                    .split(",")).toList();

            log.info("Varios numeros sendo computados");

        }else {

            studentsNumbers.add(message.phoneNumber);
            log.info("Apenas um numero sendo computado");
        }

        List<String> resultList = new ArrayList<>();

        for (String number : studentsNumbers){

            if(number.length() != 11) {
                throw new RuntimeException("Numero invalido");
            }

            String result = this.sendToWhatsapp(message.principal, number);
            resultList.add(result);

        }

        System.out.println(resultList);

    }

    private String sendToWhatsapp(String message, String phone){

        // Arrange field for Selenium interaction
        WebDriver webDriver = seleniumManager.getWebDriver();
        log.info("Pegando o Web driver para usar");

        if (Objects.isNull(webDriver)){
            throw new RuntimeException("Start the browser first ...");
        }

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        log.info("Wait configurado");

        // old xpath = "//footer//div[@role='textbox' and @contenteditable='true']"
        String textInputPath = "//footer//div[@role='textbox' and @contenteditable='true']";
        log.info("Text input variavel carregada {}", textInputPath);

        // old button interation String sendButton = "(//*[@id=\"main\"]/footer//button)[last()]";

        webDriver.get("https://web.whatsapp.com/send?phone=" + TelephoneFormatter.telephoneFormatter(phone));
        log.info("Whatsapp aberto na conversa com o numero");

        try {

            WebElement textInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(textInputPath)
                    )
            );

            log.info("Text input foi encontrado");

            textInput.sendKeys(message);
            log.info("Mensagem escrita no text input");

            textInput.sendKeys(Keys.ENTER);
            log.info("Mensagem enviada");

        }catch (Exception e) {
            System.out.println(e.getMessage());
            return "Send message ERROR";
        }

        return "Send message SUCCESS";
    }

}
