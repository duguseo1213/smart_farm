package com.ssafy.WeCanDoFarm.server.domain.device.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name="devices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    Long deviceId;

    @Column(name = "device_status")
    @Enumerated(EnumType.STRING)
    DeviceStatus deviceStatus;

    @Column(name = "stream_key")
    String streamKey;

    @Column(name = "registered_date")
    @CreatedDate
    private Date registeredDate;

    public static Device create(DeviceStatus deviceStatus,String streamKey,Date registeredDate){
        Device device = new Device();
        device.deviceStatus = deviceStatus;
        device.streamKey = streamKey;
        device.registeredDate = registeredDate;
        return device;
    }
    public static Device create(DeviceStatus deviceStatus,String streamKey){
        Device device = new Device();
        device.deviceStatus = deviceStatus;
        device.streamKey = streamKey;
        device.registeredDate = new Date();
        return device;
    }

}
