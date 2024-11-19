package com.ssafy.WeCanDoFarm.server.domain.crop.repository;

import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;

import java.util.List;

public interface CropQueryRepository {

    void deleteCropByName(String CropName);

    List<String> getCropNamesByGardenId(Long gardenId);

    List<String> recommandCropByCropName(String cropName);

    Crop getCrop(Long gardenId, String cropName);
}
