package com.accesses.administrative_system.dto.student;

import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record StudentRequestDTO(

    @NotNull(message = "O primeiro nome nao pode ser nulo")
    String firstName,

    @NotNull(message = "O segundo nome nao pode ser nulo")
    String secondName,

    @NotNull(message = "O dia de nascimento nao pode ser nulo")
    String birthDay,

    @NotNull(message = "O telefone nao pode ser nulo")
    String telephone,

    @NotNull(message = "O dia que comecou as aulas nao pode ser nulo")
    String enrollDay,

    @NotNull(message = "O campo nao pode ser nulo")
    boolean isDependent,

    String parentName,

    String parentPhone

) {
}
