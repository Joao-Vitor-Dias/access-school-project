package com.accesses.administrative_system.controller;


import com.accesses.administrative_system.service.SseAlertService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class AlertController {

    private final SseAlertService service;

    public AlertController(SseAlertService service) {
        this.service = service;
    }

    @GetMapping(value = "subscribe",
                produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(){
        return service.subscribe();
    }

}
