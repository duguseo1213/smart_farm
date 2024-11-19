package com.ssafy.WeCanDoFarm.server.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    private final String url;
    private final int port;
    private final int qos;
    private final String topic;

    public MqttProperties(String url, int port, int qos, String topic) {
        this.url = url;
        this.port = port;
        this.qos = qos;
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public int getQos() {
        return qos;
    }

    public String getTopic() {
        return topic;
    }

    public String connectionInfo() {
        return url + ":" + port;
    }
}
