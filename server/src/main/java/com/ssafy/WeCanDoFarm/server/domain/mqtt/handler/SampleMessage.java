package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import lombok.Data;

@Data
public class SampleMessage {
    private String title;
    private String content;

    // 기본 생성자
    public SampleMessage() {}

    // 매개변수 생성자
    public SampleMessage(String title, String content) {
        this.title = title;
        this.content = content;
    }

}