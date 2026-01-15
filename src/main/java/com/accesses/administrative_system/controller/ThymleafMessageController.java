package com.accesses.administrative_system.controller;

import com.accesses.administrative_system.message_api.GenericMessageRequest;
import com.accesses.administrative_system.message_api.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class ThymleafMessageController {

    @GetMapping("/start")
    public String startWhatsapp(){

        startAsync();
        return "home";
    }

    @Async
    public String startAsync(){
        try {
            Message.startWhatsapp();
        }catch (Exception e){
            System.out.println("Erro ao tentar iniciar o whatsapp");
        }finally {
            return "home";
        }
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
    public String sendMessage(@ModelAttribute GenericMessageRequest messageRequest, Model model) throws InterruptedException {
        System.out.println("Campos do front com numero: " + messageRequest);
        Message.sendGenericMessageWay(messageRequest);
        model.addAttribute("historico", Message.getHistoryNumber());
        return "home";

    }

    @GetMapping("/quit")
    public String quitWhatsapp(){

        try {
            Message.quitWhatsapp();
        }catch (Exception e){
            System.out.println("Erro ao tentar encerrar o whatsapp");
        }

        return "home";
    }

}
