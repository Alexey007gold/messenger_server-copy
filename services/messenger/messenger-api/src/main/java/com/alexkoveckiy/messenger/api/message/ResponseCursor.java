package com.alexkoveckiy.messenger.api.message;

/**
 * Created by alex on 31.03.17.
 */
public class ResponseCursor {

    private Boolean moreExists;

    public ResponseCursor(Boolean moreExists) {
        this.moreExists = moreExists;
    }
}
