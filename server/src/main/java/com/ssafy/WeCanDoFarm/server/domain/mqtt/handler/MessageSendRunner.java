//package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;
//
//import com.ssafy.WeCanDoFarm.server.core.configuration.MqttConfig;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageSendRunner implements ApplicationRunner { // (1)
//
//    private final MqttConfig.MqttOutboundGateway mqttOutboundGateway;
//
//    public MessageSendRunner(MqttConfig.MqttOutboundGateway mqttOutboundGateway) {
//        this.mqttOutboundGateway = mqttOutboundGateway;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//        mqttOutboundGateway.publish(new GardenDataMessage("1", "2","3","4"));
//    }
//}
