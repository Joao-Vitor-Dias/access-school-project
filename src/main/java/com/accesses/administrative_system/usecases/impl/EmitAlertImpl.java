package com.accesses.administrative_system.usecases.impl;

import com.accesses.administrative_system.entity.Alert;
import com.accesses.administrative_system.service.SseAlertService;
import com.accesses.administrative_system.usecases.EmitAlert;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class EmitAlertImpl implements EmitAlert {

    private final SseAlertService service;

    public EmitAlertImpl(SseAlertService service) {
        this.service = service;
    }

    @Override
    public void execute(Alert alert) {

        if (Objects.isNull(alert.getData())){
            alert.setData("...");
        }

        alert.setTimestamp(LocalDateTime.now());

        service.emmit(alert);

    }

}
