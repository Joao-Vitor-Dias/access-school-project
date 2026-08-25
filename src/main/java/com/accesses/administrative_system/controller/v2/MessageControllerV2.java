package com.accesses.administrative_system.controller.v2;

import com.accesses.administrative_system.message_api.v2.models.Message;
import com.accesses.administrative_system.message_api.v2.MessageServiceV2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("v2/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageControllerV2 {

    private final MessageServiceV2 messageServiceV2;

    public MessageControllerV2(MessageServiceV2 messageServiceV2) {
        this.messageServiceV2 = messageServiceV2;
    }

    // O connect sempre se refere ao Selenium e não ao whatsapp

    @GetMapping("connect")
    public void connect(){

        messageServiceV2.connect();

    }

    @GetMapping("disconnect")
    public void disconnect(){

        messageServiceV2.disconnect();

    }

    @PostMapping("send")
    public void sendMessage(@RequestBody Message message){

        messageServiceV2.sendWithSimpleParam(message);

    }

    @GetMapping("qr")
    public ResponseEntity<byte[]> getQr() throws IOException {

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(messageServiceV2.getQrCodeForAuth());

    }

    @GetMapping("close/popup")
    public void closePopup(){

        messageServiceV2.closePopUp();

    }

}
