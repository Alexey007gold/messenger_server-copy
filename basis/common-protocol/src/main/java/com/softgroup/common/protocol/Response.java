package com.softgroup.common.protocol;

public class Response<T extends ActionData> extends RoutedAction<T> {
	private static final long serialVersionUID = 8979170551734666755L;

	private ResponseStatus status;

    public Response() {
    }

    public Response(ActionHeader header, T data, ResponseStatus status) {
        super(header, data);
        this.status = status;
    }

    public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

}
