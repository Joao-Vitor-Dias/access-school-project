package com.accesses.administrative_system.controller;

import com.accesses.administrative_system.message_api.Message;
import com.accesses.administrative_system.message_api.MessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageRequest messageRequest) throws JsonProcessingException {

        Message.sendMenssage(messageRequest);

    }

}
