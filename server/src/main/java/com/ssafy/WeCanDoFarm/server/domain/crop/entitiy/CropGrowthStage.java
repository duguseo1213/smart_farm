package com.ssafy.WeCanDoFarm.server.domain.crop.entitiy;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Table(name = "crop_growth_stage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropGrowthStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_growth_stage_id")
    private Long cropGrowthStageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @Column(name="growth_percentage")
    @CreationTimestamp
    private int growthPercentage;

    @Column(name = "created_date")
    private Date createdDate;

    public static CropGrowthStage create(Crop crop, int growthPercentage) {
        CropGrowthStage cropGrowthStage = new CropGrowthStage();
        cropGrowthStage.crop = crop;
        cropGrowthStage.growthPercentage = growthPercentage;
        cropGrowthStage.createdDate = new Date();
        return cropGrowthStage;
    }
}
