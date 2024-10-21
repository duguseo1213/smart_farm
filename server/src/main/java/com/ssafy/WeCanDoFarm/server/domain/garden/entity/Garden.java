package com.ssafy.WeCanDoFarm.server.domain.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "created_date")
    private Date createdDate;

    public static Garden create(String gardenName, String gardenImage, Date createdDate) {
        Garden garden = new Garden();
        garden.gardenName = gardenName;
        garden.gardenImage = gardenImage;
        garden.createdDate = createdDate;
        return garden;
    }

}
