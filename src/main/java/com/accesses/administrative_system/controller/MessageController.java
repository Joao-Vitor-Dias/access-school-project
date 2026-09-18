package com.accesses.administrative_system.controller;

import com.accesses.administrative_system.message_api.Message;
import com.accesses.administrative_system.message_api.MessageStudentRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/message")
@Deprecated
public class MessageController {

    @GetMapping("/start")
    public void startWhatsapp(){

        Message.startWhatsapp();

    }

    @GetMapping("/qr")
    public ResponseEntity<byte[]> getQrCode() throws IOException {

        byte [] qrCodeImage = Message.getQrCode();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(qrCodeImage);

    }

    @GetMapping("/print")
    public ResponseEntity<byte[]> getPrint() throws IOException {

        byte [] image = Message.getPrint();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);

    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageStudentRequest messageStudentRequest) throws InterruptedException {

        Message.sendMessage(messageStudentRequest);

    }

    @GetMapping("/quit")
    public void quitWhatsapp(){

        Message.quitWhatsapp();

    }

}
