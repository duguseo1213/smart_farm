package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class FunctionMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(SampleMessageHandler.class);

    public void handle(Message<FunctionMessage> message) {
        log.info("message arrived : {}", message);
    }
}
