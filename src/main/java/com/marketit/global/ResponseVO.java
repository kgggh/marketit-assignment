package com.marketit.global;

import com.marketit.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ResponseVO {
    private int code;
    private String message;

    private ResponseVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseVO(ErrorCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public static ResponseVO success() {
        return new ResponseVO(1,"Success");
    }

    public static ResponseVO fail(ErrorCode code, String message) {
        return new ResponseVO(code, message);
    }
}
