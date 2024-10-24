package com.ssafy.WeCanDoFarm.server.domain.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name="gardens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garden_id", nullable = false)
    private Long gardenId;

    @Column(name = "garden_name")
    private String gardenName;

    @Column(name = "garden_image")
    private String gardenImage;

    @Column(name = "garden_address")
    private String gardenAddress;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "device_sn")
    private Long deviceSN;



    public static Garden create(String gardenName, String gardenImage, String gardenAddress, Long deviceSN) {
        Garden garden = new Garden();
        garden.gardenName = gardenName;
        garden.gardenImage = gardenImage;
        garden.gardenAddress = gardenAddress;
        garden.deviceSN = deviceSN;
        return garden;
    }

}
