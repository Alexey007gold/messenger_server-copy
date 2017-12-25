package com.alexkoveckiy.common.protocol;

import java.io.Serializable;

public abstract class Action<T extends Serializable> {

    private ActionHeader header;

    private T data;

    public Action() {
    }

    public Action(ActionHeader header, T data) {
        this.header = header;
        this.data = data;
    }

    public ActionHeader getHeader() {
        return header;
    }

    public void setHeader(ActionHeader header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
