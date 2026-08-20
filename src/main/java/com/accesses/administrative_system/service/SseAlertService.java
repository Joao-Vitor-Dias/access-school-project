package com.accesses.administrative_system.service;

import com.accesses.administrative_system.entity.Alert;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseAlertService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe(){

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));

        emitter.onTimeout(() -> emitters.remove(emitter));

        emitter.onError(error -> emitters.remove(emitter));

        return emitter;

    }

    public void emmit(Alert alert){

        emitters.forEach(e -> {
                    try {
                        e.send(alert);
                    }catch (Exception exception){
                        e.complete();
                        emitters.remove(e);
                    }
                });

    }


}
