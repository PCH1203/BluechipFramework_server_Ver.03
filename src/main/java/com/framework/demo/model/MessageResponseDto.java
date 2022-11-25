package com.framework.demo.model;

import com.framework.demo.enums.HttpStatusCode;
import lombok.Data;

@Data
public class MessageResponseDto {


    private HttpStatusCode status;
    private int statusCode;
    private String message;
    private Object data;

    public MessageResponseDto() {
        this.statusCode = 200;
        this.status = HttpStatusCode.OK;
        this.data = null;
        this.message = null;
    }

    public MessageResponseDto(String msg) {
        this.statusCode = 200;
        this.status = HttpStatusCode.OK;
        this.message = msg;
        this.data = null;
    }

    public MessageResponseDto(Object data, String msg) {
        this.statusCode = 200;
        this.status = HttpStatusCode.OK;
        this.data = data;
        this.message = msg;
    }

    public MessageResponseDto(HttpStatusCode code, Object data, String msg) {
        this.statusCode = code.getStatusCode();
        this.status = code;
        this.data = data;
        this.message = msg;

    }

}

