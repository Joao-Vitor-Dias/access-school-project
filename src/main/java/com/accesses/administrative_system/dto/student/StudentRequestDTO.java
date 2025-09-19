package com.accesses.administrative_system.dto.student;

import jakarta.validation.constraints.*;

public record StudentRequestDTO(

    @NotNull(message = "O primeiro nome nao pode ser nulo")
    String firstName,

    @NotNull(message = "O segundo nome nao pode ser nulo")
    String secondName,

    @NotNull(message = "O dia de nascimento nao pode ser nulo")
    String birthDay,

    // (19) 99764-1308
    @Size(max = 15)
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Telefone deve ter o formato (XX) XXXXX-XXXX")
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
