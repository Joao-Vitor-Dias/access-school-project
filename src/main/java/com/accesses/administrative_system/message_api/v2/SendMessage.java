package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.message_api.v2.models.Message;

public interface SendMessage {

    void execute(Message message);

}
