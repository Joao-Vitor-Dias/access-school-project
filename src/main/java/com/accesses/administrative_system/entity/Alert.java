package com.accesses.administrative_system.entity;

import com.accesses.administrative_system.entity.enums.AlertType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Alert {

    AlertType alertType;

    String message;

    LocalDateTime timestamp;

    Object data;

}
