package com.framework.demo.model;

import com.framework.demo.enums.HttpStatusCode;
import lombok.Data;

@Data
public class MessageResponseDto {


    private HttpStatusCode statusCode;
    private int status;
    private String message;
    private Object data;

    public MessageResponseDto() {
        this.statusCode = HttpStatusCode.OK;
        this.status = 200;
        this.data = null;
        this.message = null;
    }

    public MessageResponseDto(String msg) {
        this.statusCode = HttpStatusCode.OK;
        this.status = 200;
        this.message = msg;
        this.data = null;
    }

    public MessageResponseDto(Object data, String msg) {
        this.statusCode = HttpStatusCode.OK;
        this.status = 200;
        this.data = data;
        this.message = msg;
    }



    public MessageResponseDto(HttpStatusCode code, Object data, String msg) {
        this.statusCode = code;
        this.status = code.getStatusCode();
        this.data = data;
        this.message = msg;

    }

}

