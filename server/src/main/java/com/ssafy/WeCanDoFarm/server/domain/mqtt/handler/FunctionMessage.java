package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import lombok.Data;

@Data
public class FunctionMessage {
    private int functionId;
    private String functionName;

    public FunctionMessage(){

    }

    public FunctionMessage(int functionId, String functionName) {
        this.functionId = functionId;
        this.functionName = functionName;
    }

}
