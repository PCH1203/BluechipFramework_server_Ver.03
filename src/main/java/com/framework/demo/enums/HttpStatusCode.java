package com.framework.demo.enums;

import lombok.Getter;

@Getter
public enum HttpStatusCode {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    HttpStatusCode(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }

}
