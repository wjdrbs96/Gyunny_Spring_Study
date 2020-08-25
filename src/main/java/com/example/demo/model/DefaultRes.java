package com.example.demo.model;

import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {

    private int statusCode;
    private String responseMessage;
    private T data;


    public DefaultRes(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
}
