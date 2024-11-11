package com.ssafy.WeCanDoFarm.server.domain.garden.controller;


import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.PlantDiseaseDto;
import com.ssafy.WeCanDoFarm.server.domain.garden.service.GardenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v2/garden")
@RequiredArgsConstructor
public class GardnIoTController {

    private final GardenService gardenService;

    @PostMapping("/plant-disease-detection")
    public SuccessResponse<PlantDiseaseDto.PlantDiseaseResponse> detectPlantDisease(PlantDiseaseDto.PlantDiseaseRequest request) throws Exception {

        return SuccessResponse.of(gardenService.plantDiseaseDetection(request.getFile()));
    }
}
