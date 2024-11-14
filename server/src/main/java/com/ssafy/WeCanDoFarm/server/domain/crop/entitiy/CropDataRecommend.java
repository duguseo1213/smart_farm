package com.ssafy.WeCanDoFarm.server.domain.crop.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "crop_data_recommand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropDataRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_data_recommand_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_crop_id")
    private Crop mainCrop;

    @Column(name = "crop_recommand")
    private String cropRecommand;

    public CropDataRecommend create(Crop mainCrop, String cropRecommand) {
        CropDataRecommend cropDataRecommend = new CropDataRecommend();
        cropDataRecommend.mainCrop = mainCrop;
        cropDataRecommend.cropRecommand = cropRecommand;
        return cropDataRecommend;
    }
}
