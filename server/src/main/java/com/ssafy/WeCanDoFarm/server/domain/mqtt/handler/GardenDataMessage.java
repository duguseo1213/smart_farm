package com.ssafy.WeCanDoFarm.server.domain.mqtt.handler;

import lombok.Data;

@Data
public class GardenDataMessage {
    private double temperature;
    private double humidity;
    private double illuminance;
    private double soil_moisture;

    // 기본 생성자
    public GardenDataMessage() {}

    // 매개변수 생성자
    public GardenDataMessage(String temperature, String humidity, String illuminance, String soilMoisture) {
        this.temperature = Double.parseDouble(temperature);
        this.humidity = Double.parseDouble(humidity);
        this.illuminance = Double.parseDouble(illuminance);
        this.soil_moisture = Double.parseDouble(soilMoisture);
    }


}
