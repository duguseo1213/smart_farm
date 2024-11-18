package com.ssafy.WeCanDoFarm.server.domain.crop.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropGrowthStageDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.CropGrowthStage;
import com.ssafy.WeCanDoFarm.server.domain.crop.service.CropService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/crop")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping("/add-crop")
    @Operation(summary = "작물 추가",description = "작물1&작물2&작물3 형태로 전송시 추가됨")
    public SuccessResponse<Void> addCrop(CropDto.AddCropRequest request){
        cropService.addCrop(request);
        return SuccessResponse.empty();
    }

    @PostMapping("/delete-crop")
    @Operation(summary = "작물 삭제",description = "작물1&작물2&작물3 형태로 전송시 제거됨")
    public SuccessResponse<Void> deleteCrop(CropDto.DeleteCropRequest request){
        cropService.deleteCrop(request);
        return SuccessResponse.empty();
    }

    @GetMapping("/get-crops/{gardenId}")
    @Operation(summary = "작물 조회",description = "작물 조회")
    public SuccessResponse<List<String>> deleteCrop(@PathVariable Long gardenId){
        ;
        return SuccessResponse.of(cropService.getCrop(gardenId));
    }

    @PostMapping("/recommand-crop")
    public SuccessResponse<List<String>> recommandCrop(String cropName){

        return SuccessResponse.of(cropService.recommendCrop(cropName));
    }

    @PostMapping("/create-crop-growth-stage")
    public SuccessResponse<Void> createCropGrowthStage(CropGrowthStageDto.CropGrowthStageRequest request){
        cropService.addCropGrowthStage(request);
        return SuccessResponse.empty();
    }

    @GetMapping("/get-crop-growth-stages")
    public SuccessResponse<List<CropGrowthStage>> getCropGrowthStages(CropGrowthStageDto.getCropGrowthsStageRequest request){
        return SuccessResponse.of(cropService.getCropGrowthStage(request.getGardenId(), request.getCropName()));
    }
}
