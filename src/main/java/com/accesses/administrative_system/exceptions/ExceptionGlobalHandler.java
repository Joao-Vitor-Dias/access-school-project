package com.accesses.administrative_system.exceptions;

import com.accesses.administrative_system.entity.Alert;
import com.accesses.administrative_system.entity.enums.AlertType;
import com.accesses.administrative_system.usecases.EmitAlert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    private final EmitAlert emitAlert;

    public ExceptionGlobalHandler(EmitAlert emitAlert) {
        this.emitAlert = emitAlert;
    }

    @ExceptionHandler(SeleniumNotInitializedException.class)
    public void handleSeleniumException(SeleniumNotInitializedException ex){

        emitAlert.execute(Alert.builder()
                .message("O Driver do Selenium ainda não foi inicializado")
                .alertType(AlertType.CONNECT_DRIVER_FAILURE)
                .build());

    }

    @ExceptionHandler(InvalidMessageException.class)
    public void handleSeleniumException(InvalidMessageException ex){

        emitAlert.execute(Alert.builder()
                .message("A mensagem que tentou enviar não é valida")
                .alertType(AlertType.MESSAGE_SEND_FAILURE)
                .build());

    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public void handleSeleniumException(InvalidPhoneNumberException ex){

        emitAlert.execute(Alert.builder()
                .message("Número que você tentou mandar mensagem é invalido")
                .alertType(AlertType.MESSAGE_SEND_FAILURE)
                .build());

    }

    @ExceptionHandler(QrAuthException.class)
    public void handleSeleniumException(QrAuthException ex){

        emitAlert.execute(Alert.builder()
                .message("Houve um problema na autenticação verifique se está conectado ... Caso esteja conectado, tente novamente")
                .alertType(AlertType.LOGIN_WHATSAPP_FAILURE)
                .build());

    }



}
