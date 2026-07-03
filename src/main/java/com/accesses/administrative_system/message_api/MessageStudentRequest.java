package com.accesses.administrative_system.message_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageStudentRequest {

    private List<Long> studentsId;
    private String message;
    private boolean isDefaultMessage;

}
