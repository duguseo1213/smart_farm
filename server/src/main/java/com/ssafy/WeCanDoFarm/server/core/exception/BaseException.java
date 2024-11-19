package com.ssafy.WeCanDoFarm.server.core.exception;

import org.springframework.validation.Errors;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public BaseException(Throwable cause) {
        super(cause);
        this.errorCode = ErrorCode.SERVER_ERROR;
        this.message = cause.getMessage();
    }

    public BaseException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}