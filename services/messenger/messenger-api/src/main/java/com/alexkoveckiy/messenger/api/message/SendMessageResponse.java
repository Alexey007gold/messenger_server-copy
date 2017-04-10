package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.MessageDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * Created by alex on 31.03.17.
 */
public class SendMessageResponse implements ResponseData {
    private static final long serialVersionUID = -3502443003876744747L;

    private MessageDTO message;

    public SendMessageResponse(MessageDTO message) {
        this.message = message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }
}
