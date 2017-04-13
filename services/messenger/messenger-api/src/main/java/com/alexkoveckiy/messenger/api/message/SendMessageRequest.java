package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.messenger.api.dto.MessageRequestDTO;

/**
 * Created by alex on 31.03.17.
 */
public class SendMessageRequest implements RequestData {
    private static final long serialVersionUID = 5525111933017728212L;

    private MessageRequestDTO message;

    public SendMessageRequest() {
    }

    public SendMessageRequest(MessageRequestDTO message) {
        this.message = message;
    }

    public MessageRequestDTO getMessage() {
        return message;
    }
}
