package com.ssafy.WeCanDoFarm.server.domain.crop.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;
import com.ssafy.WeCanDoFarm.server.domain.crop.service.CropService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/crop")
@RequiredArgsConstructor
public class CropController {

    private CropService cropService;

    @PostMapping("/add-crop")
    public SuccessResponse<Void> addCrop(@RequestBody CropDto.AddCropRequest request){
        cropService.addCrop(request);
        return SuccessResponse.empty();
    }

    @PostMapping("/delete-crop")
    public SuccessResponse<Void> deleteCrop(@RequestBody CropDto.DeleteCropRequest request){
        cropService.deleteCrop(request);
        return SuccessResponse.empty();
    }
}
