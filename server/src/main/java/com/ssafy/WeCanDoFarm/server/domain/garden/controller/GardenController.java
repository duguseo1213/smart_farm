package com.ssafy.WeCanDoFarm.server.domain.garden.controller;


import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.PlantDiseaseDto;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterUserToGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.service.GardenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/garden")
@RequiredArgsConstructor
public class GardenController {

    private final GardenService gardenService;

    @GetMapping("/{username}")
    public SuccessResponse<List<Garden>> getGardenList(@PathVariable String username) throws Exception {

        return SuccessResponse.of(gardenService.getGardens(username));

    }

    @PostMapping("/add-garden")
    public SuccessResponse<String> registerGarden(@RequestBody RegisterGardenRequest request) throws Exception {
        gardenService.registerGarden(request);
        return SuccessResponse.of("텃밭 등록완료");
    }

    @PostMapping("/add-user-to-garden")
    public SuccessResponse<String> registerUserToGarden(@RequestBody RegisterUserToGardenRequest request) throws Exception {

        gardenService.registerUserToGarden(request);
        return SuccessResponse.empty();
    }

    @PostMapping("/plant-disease-detection")
    public SuccessResponse<PlantDiseaseDto.PlantDiseaseResponse> plantDiseaseDetection(@ModelAttribute PlantDiseaseDto.PlantDiseaseRequest request) throws Exception {
        return SuccessResponse.of(PlantDiseaseDto.PlantDiseaseResponse.empty());
    }

}


