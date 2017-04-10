package com.alexkoveckiy.common.protocol;

public class Response<T extends ResponseData> extends Action<T> {

	private ResponseStatus status;

    public Response() {
    }

    public Response(T data) {
        super(null, data);
    }

    public Response(ResponseStatus responseStatus) {
        this.status = responseStatus;
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
