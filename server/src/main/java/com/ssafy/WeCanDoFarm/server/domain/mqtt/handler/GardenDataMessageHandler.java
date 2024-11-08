package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import com.ssafy.WeCanDoFarm.server.domain.garden.service.GardenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GardenDataMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(SampleMessageHandler.class);
    private final GardenService gardenService;
    public void handle(Message<GardenDataMessage> message) {
        MessageHeaders headers = message.getHeaders();
        String topic = (String) headers.get("mqtt_receivedTopic"); // 토픽 정보 추출
        log.info("Message arrived on topic '{}': {}", topic, message.getPayload());
        try {
            Long deviceId = Long.parseLong(topic.split("/")[1]);
            log.info("Device ID: {}", deviceId);
        }catch (NullPointerException e){
            log.info("mqtt device id is invalid: {}", topic);
        }


    }
}
