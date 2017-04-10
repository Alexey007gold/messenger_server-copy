package com.alexkoveckiy.common.protocol;

import java.util.UUID;

import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.OK;

/**
 * Created by alex on 06.04.17.
 */
public class ResponseFactory {

    public static <T extends ResponseData> Response<T> createResponse(Request<?> req, T data) {
        Response<T> response = new Response<>();
        setHeader(req, response);
        response.setData(data);
        response.setStatus(new ResponseStatus(OK.code, OK.name()));
        return response;
    }

    public static <T extends ResponseData> Response<T> createResponse(Request<?> req, Status status) {
        Response<T> response = new Response<>();
        setHeader(req, response);
        response.setStatus(new ResponseStatus(status.code, status.name()));
        return response;
    }

    public static <T extends ResponseData> Response<T> createResponse(ActionHeader header, T data) {
        Response<T> response = new Response<>();
        response.setHeader(header);
        response.setData(data);
        response.setStatus(new ResponseStatus(OK.code, OK.name()));
        return response;
    }

    public static <T extends ResponseData> Response<T> createResponse(Request<?> req, Status status, String message) {
        Response<T> response = new Response<>();
        setHeader(req, response);
        response.setStatus(new ResponseStatus(status.code, status.name() + " (" + message + ")"));
        return response;
    }

    private static <T extends ResponseData> void setHeader(Request<?> req, Response<T> response) {
        response.setHeader(new ActionHeader(UUID.randomUUID().toString(), req.getHeader().getUuid(),
                req.getHeader().getCommand(), req.getHeader().getType(), req.getHeader().getVersion()));
    }

    public enum Status {
        OK(200),
        BAD_REQUEST(400),
        FORBIDDEN(403),
        NOT_FOUND(404),
        NOT_ACCEPTABLE(422),
        UNPROCESSABLE_ENTITY(422),
        TOO_MANY_REQUESTS(429),
        INTERNAL_SERVER_ERROR(500),
        NOT_IMPLEMENTED(501);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
