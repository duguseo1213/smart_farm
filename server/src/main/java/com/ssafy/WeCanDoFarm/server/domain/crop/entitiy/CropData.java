package com.ssafy.WeCanDoFarm.server.domain.crop.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "crop_data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_data_id")
    Long id;

    @Column(name = "crop_type")
    String cropType;

    @Column(name = "total_growth_week")
    Integer totalGrowthWeek;
}
