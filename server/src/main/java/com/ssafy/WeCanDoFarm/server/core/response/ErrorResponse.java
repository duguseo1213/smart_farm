package com.ssafy.WeCanDoFarm.server.core.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import lombok.Getter;

public class ErrorResponse extends BaseResponse<Void>{

    @JsonIgnore
    private Void data;

    @Getter
    private Object reference;

    private ErrorResponse() {
        this.success = false;
        this.status= ErrorCode.SERVER_ERROR.getCode();
        this.message = ErrorCode.SERVER_ERROR.getMessage();
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.success = false;
        this.status = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(ErrorCode errorCode, Object reference) {
        this.success = false;
        this.status = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.reference = reference;
    }

    public static ErrorResponse empty() {return new ErrorResponse();}
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
    public static ErrorResponse of(ErrorCode errorCode, Object reference) {return new ErrorResponse(errorCode, reference);
    }
}
