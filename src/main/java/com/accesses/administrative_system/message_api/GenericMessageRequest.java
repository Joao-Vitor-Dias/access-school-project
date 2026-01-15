package com.accesses.administrative_system.message_api;

import jakarta.validation.constraints.Size;

public record GenericMessageRequest(
        // If numbers in request is more then one they need use ',' for separe
        String numbers,
        String message
) {
}
