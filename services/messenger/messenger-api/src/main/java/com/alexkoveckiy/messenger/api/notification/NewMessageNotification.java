package com.alexkoveckiy.messenger.api.notification;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.messenger.api.dto.MessageDTO;

/**
 * Created by alex on 03.04.17.
 */
public class NewMessageNotification implements ResponseData {
    private static final long serialVersionUID = -8128071955013431676L;

    private MessageDTO message;

    public NewMessageNotification(MessageDTO message) {
        this.message = message;
    }
}
