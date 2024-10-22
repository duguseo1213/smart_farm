package com.ssafy.WeCanDoFarm.server.domain.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Table(name="garden_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GardenStatus {

    @Id
    @Column(name = "garden_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "garden_id")
    private Garden garden;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "illuminance")
    private Double illuminance;

    @Column(name = "soil_moisture")
    private Double soil_moisture;

    @Column(name = "createdDate")
    @CreatedDate
    private Date createdDate;

    public static GardenStatus create(Garden garden,Double humidity, Double illuminance, Double soil_moisture, Date createdDate){
        GardenStatus gardenStatus = new GardenStatus();
        gardenStatus.garden = garden;
        gardenStatus.humidity = humidity;
        gardenStatus.illuminance = illuminance;
        gardenStatus.soil_moisture = soil_moisture;
        gardenStatus.createdDate = createdDate;
        return gardenStatus;
    }
}
