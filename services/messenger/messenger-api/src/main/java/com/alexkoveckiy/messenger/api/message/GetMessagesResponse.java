package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.messenger.api.dto.MessageDTO;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetMessagesResponse implements ResponseData {
    private static final long serialVersionUID = 3929687521072686401L;

    private List<MessageDTO> messages;
    private Integer totalUnread;
    private ResponseCursor cursor;

    public GetMessagesResponse(List<MessageDTO> messages, Integer totalUnread, ResponseCursor cursor) {
        this.messages = messages;
        this.totalUnread = totalUnread;
        this.cursor = cursor;
    }
}
