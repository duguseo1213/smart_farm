package com.ssafy.WeCanDoFarm.server.domain.crop.service;

import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;
import com.ssafy.WeCanDoFarm.server.domain.crop.repository.CropRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;
    private final GardenRepository gardenRepository;


    public void addCrop(CropDto.AddCropRequest request) {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow();
        String[] list = request.getCrops().split("&");
        for(String crop : list) {
            cropRepository.save(Crop.create(garden,crop));
        }
    }



    public void deleteCrop(CropDto.DeleteCropRequest request) {
        String[] list = request.getCrops().split("&");
        for(String crop : list) {
            cropRepository.deleteCropByName(crop);
        }
    }
}
