package com.accesses.administrative_system.message_api.v2.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {


    // number phone can be one or more in the same string, ex:
    // 11111111111 or 11111111111, 11111111122, 11111111133 ...
    String phoneNumber;

    // Content of message
    String principal;

    public Message(String numberPhone, String principal) {
        this.phoneNumber = numberPhone.replace(" ", "");
        this.principal = principal;
    }
}
