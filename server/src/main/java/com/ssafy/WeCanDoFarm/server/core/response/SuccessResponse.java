package com.ssafy.WeCanDoFarm.server.core.response;

public class SuccessResponse<T> extends BaseResponse<T>{

    private SuccessResponse(T data) {
        this.success = true;
        this.status = 200;
        this.message = "success";
        this.data = data;
    }

    private SuccessResponse() {
        this.success = true;
        this.status = 200;
        this.message = "success";
    }

    public static <T> SuccessResponse<T> empty() {return new SuccessResponse<T>();}

    public static <T> SuccessResponse<T> of(T data) {return new SuccessResponse<>(data);}
}
