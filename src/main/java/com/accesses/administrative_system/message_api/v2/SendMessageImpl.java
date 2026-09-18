package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.entity.Alert;
import com.accesses.administrative_system.entity.enums.AlertType;
import com.accesses.administrative_system.exceptions.InvalidMessageException;
import com.accesses.administrative_system.exceptions.InvalidPhoneNumberException;
import com.accesses.administrative_system.exceptions.SeleniumNotInitializedException;
import com.accesses.administrative_system.message_api.config.SeleniumManager;
import com.accesses.administrative_system.message_api.v2.models.Message;
import com.accesses.administrative_system.usecases.EmitAlert;
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
    private final EmitAlert emitAlert;

    public SendMessageImpl(SeleniumManager seleniumManager, EmitAlert emitAlert) {
        this.seleniumManager = seleniumManager;
        this.emitAlert = emitAlert;
    }

    @Override
    public void execute(Message message) {

        if (message.getPrincipal() == null){
            throw new InvalidMessageException("Message can`t be null");
        }

        if (message.getPhoneNumber() == null){
            throw new InvalidPhoneNumberException("Phone numbers can`t be null");
        }

        List<String> studentsNumbers = new ArrayList<>();

        // If number input(from controller) have 55 change to 13 and change phone formatter
        if (message.getPhoneNumber().length() != 11){

            studentsNumbers = Arrays.stream(message.getPhoneNumber().trim()
                    .split(",")).toList();

            log.info("Varios numeros sendo computados");
            log.info("Numeros computados {}", studentsNumbers);

        }else {

            studentsNumbers.add(message.getPhoneNumber());
            log.info("Apenas um numero sendo computado");
        }

        List<String> resultList = new ArrayList<>();

        for (String number : studentsNumbers){

            if(number.length() != 11) {
                throw new InvalidPhoneNumberException("Numero invalido");
            }

            emitAlert.execute(Alert.builder()
                    .message("Tentando enviar mensagem para " + number)
                    .alertType(AlertType.TRY_SEND_MESSAGE)
                    .data(Message.builder()
                            .phoneNumber(number)
                            .principal(message.getPrincipal())
                            .build())
                    .build());

            String result = this.sendToWhatsapp(message.getPrincipal(), number);

            resultList.add(result);

            try {
                Thread.sleep(2000);
            }catch (Exception e){
                System.out.println("Erro para dormir a thread");
            }

        }

        System.out.println(resultList);

    }

    private String sendToWhatsapp(String message, String phone){

        // Arrange field for Selenium interaction
        WebDriver webDriver = seleniumManager.getWebDriver();
        log.info("Pegando o Web driver para usar");

        if (Objects.isNull(webDriver)){
            throw new SeleniumNotInitializedException("Start the browser first ...");
        }

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        log.info("Wait driver configurado");

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

            String[] lines = message.split("\n");

            for (int i = 0; i < lines.length; i++) {

                textInput.sendKeys(lines[i]);

                if (i < lines.length - 1) {
                    textInput.sendKeys(Keys.SHIFT, Keys.ENTER);
                }
            }

            log.info("Mensagem escrita no text input");

            textInput.sendKeys(Keys.ENTER);
            log.info("Botão de enter pressionado");

            log.info("Mensagem enviada");

        }catch (Exception e) {
            System.out.println(e.getMessage());

            emitAlert.execute(Alert.builder()
                    .message("Erro ao enviar mensagem para ...")
                    .alertType(AlertType.MESSAGE_SEND_FAILURE)
                    .data(Message.builder()
                            .phoneNumber(phone)
                            .principal(message)
                            .build())
                    .build());

            return "Send message ERROR";
        }

        emitAlert.execute(Alert.builder()
                .message("Messagem enviada com sucesso")
                .alertType(AlertType.MESSAGE_SEND_SUCCESSFUL)
                .data(Message.builder()
                        .phoneNumber(phone)
                        .principal(message)
                        .build())
                .build());

        return "Send message SUCCESS";
    }

}
