package com.accesses.administrative_system.message_api;

import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.service.StudentService;
import com.accesses.administrative_system.util.TelephoneFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class Message {

    private static StudentService studentService;

    public Message(StudentService studentServiceParm){
        studentService = studentServiceParm;
    }

    public static void sendMenssage(MessageRequest messageRequest) throws JsonProcessingException {

        Student student = studentService.findStudentById(messageRequest.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Estudante nao encontrado!! "));



        String telephone = TelephoneFormatter.telephoneFormatter(student.getTelephone());

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("session", "default");
        jsonMap.put("chatId", telephone);
        jsonMap.put("text", messageRequest.getMessage());
        jsonMap.put("reply_to", null);
        jsonMap.put("linkPreview", true);
        jsonMap.put("linkPreviewHighQuality", false);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/api/sendText"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

    }

    /*
    * {
          "chatId": "5519971292054@c.us",
          "reply_to": null,
          "text": "Hello nigger!",
          "linkPreview": true,
          "linkPreviewHighQuality": false,
          "session": "default"
        }
    *
    * */
}
