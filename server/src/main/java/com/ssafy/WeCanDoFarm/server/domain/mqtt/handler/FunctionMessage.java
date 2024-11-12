package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import lombok.Data;

@Data
public class FunctionMessage {
    private int functionId;
    private String functionName;
    private Long userId;
    public FunctionMessage(){

    }

    public FunctionMessage(int functionId, String functionName, Long userId) {
        this.functionId = functionId;
        this.functionName = functionName;
        this.userId = userId;
    }

}
