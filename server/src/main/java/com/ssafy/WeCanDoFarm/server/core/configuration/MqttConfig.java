package com.ssafy.WeCanDoFarm.server.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.WeCanDoFarm.server.core.properties.MqttProperties;
import com.ssafy.WeCanDoFarm.server.domain.mqtt.handler.SampleMessage;
import com.ssafy.WeCanDoFarm.server.domain.mqtt.handler.SampleMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.integration.mqtt.support.MqttHeaders;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

    private final SampleMessageHandler sampleMessageHandler;
    private final MqttProperties mqttProperties;
    private final ObjectMapper objectMapper;

    public MqttConfig(SampleMessageHandler sampleMessageHandler, MqttProperties mqttProperties, ObjectMapper objectMapper) {
        this.sampleMessageHandler = sampleMessageHandler;
        this.mqttProperties = mqttProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(connectOptions());
        return factory;
    }

    private MqttConnectOptions connectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttProperties.connectionInfo()});
        return options;
    }

    @Bean
    public IntegrationFlow mqttInboundFlow() {
        return IntegrationFlow.from(mqttChannelAdapter())
                .transform(Transformers.fromJson(SampleMessage.class))
                .handle(message -> sampleMessageHandler.handle((SampleMessage) message.getPayload()))
                .get();
    }

    private MqttPahoMessageDrivenChannelAdapter mqttChannelAdapter() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                MqttClient.generateClientId(),
                mqttPahoClientFactory(),
                mqttProperties.getTopic()
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttProperties.getQos());
        return adapter;
    }

    @Bean
    public IntegrationFlow mqttOutboundFlow() {
        return IntegrationFlow.from(MQTT_OUTBOUND_CHANNEL)
                .transform((Object payload) -> {
                    try {
                        if (payload instanceof SampleMessage) {
                            return objectMapper.writeValueAsString(payload);
                        } else {
                            return payload;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to convert message", e);
                    }
                })
                .handle(mqttOutboundMessageHandler())
                .get();
    }

    private MessageHandler mqttOutboundMessageHandler() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(mqttProperties.getTopic());
        handler.setDefaultQos(mqttProperties.getQos());
        return handler;
    }

    @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
    public interface MqttOutboundGateway {

        @Gateway
        void publish(@Header(MqttHeaders.TOPIC) String topic, String data);

        @Gateway
        void publish(SampleMessage data);
    }

    public static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";
}
