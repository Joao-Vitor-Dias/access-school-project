package com.accesses.administrative_system.message_api;

import jakarta.validation.constraints.Size;

public record GenericMessageRequest(
        // 19 997641308
        // If numbers in request is more then one they need use ',' for separe
        @Size(max = 11, min = 11, message = "Invalid number")
        String numbers,
        String message
) {
}
