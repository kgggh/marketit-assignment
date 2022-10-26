package com.marketit.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /* common */
    NOT_FOUND(400004, 404, "Not Found Resource"),
    BAD_REQUEST(400000, 400,"Bad Request"),
    INTERNAL_SERVER_ERROR(500000, 500,"Internal Server Error"),

    /* order */
    ORDER_STATUS_CANNOT_BE_CHANGED(410000, 500,""),
    ORDER_NOT_EXIST(410004, 500,""),

    /* item */
    ITEM_NOT_EXIST(420004, 500,"");

    private int code;
    private int status;
    private String reason;

    ErrorCode(int code, int status) {
        this.code = code;
        this.status = status;
    }

    ErrorCode(int code, int status, String reason) {
        this.code = code;
        this.status = status;
        this.reason = reason;
    }
}
