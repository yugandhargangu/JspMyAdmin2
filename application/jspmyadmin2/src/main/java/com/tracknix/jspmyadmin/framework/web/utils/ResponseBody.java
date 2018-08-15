package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBody<T> {
    public static final int MYSQL_ERROR = 500;
    public static final int PARAMS_ERROR = 400;
    @JsonProperty
    private int code;
    @JsonProperty
    private String message;
    @JsonProperty
    private T body;

    public ResponseBody() {
        this.code = 200;
    }

    public ResponseBody<T> error(int code) {
        this.code = code;
        return this;
    }

    public ResponseBody<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBody<T> body(T body) {
        this.body = body;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }
}
