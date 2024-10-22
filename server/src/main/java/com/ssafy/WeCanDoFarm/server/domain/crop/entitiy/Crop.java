package com.ssafy.WeCanDoFarm.server.domain.crop.entitiy;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "crops")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_id")
    private Long cropId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "garden_id")
    private Garden garden;

    @Column(name = "crop_nickname")
    private String cropNickname;

    @Column(name="crop_name")
    private String cropName;

    @Column(name = "growth_stage")
    @Enumerated(EnumType.STRING)
    private Growth growthStage;

    public static Crop create(Garden garden, String cropNickname, String cropName) {
        Crop crop = new Crop();
        crop.garden = garden;
        crop.cropNickname = cropNickname;
        crop.cropName = cropName;
        crop.growthStage = Growth.Seed;
        return crop;
    }
}
