package com.alexkoveckiy.messenger.api.message;

/**
 * Created by alex on 31.03.17.
 */
public class RequestCursor {

    private Integer count;
    private Integer offset;

    public RequestCursor() {
    }

    public RequestCursor(Integer count, Integer offset) {
        this.count = count;
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getOffset() {
        return offset;
    }
}
