package com.ssafy.WeCanDoFarm.server.domain.garden.controller;


import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import com.ssafy.WeCanDoFarm.server.domain.garden.service.GardenService;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
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

    @GetMapping("/get-gardens/{username}")
    public SuccessResponse<List<GetGardenResponse>> getGarden(@PathVariable String username) throws Exception {
        List<Garden> gardenList = gardenService.getGardens(username);
        List<GetGardenResponse> gardenResponseList = new ArrayList<>();

        for(int i=0;i<gardenList.size();i++){

            Garden garden = gardenList.get(i);
            GetGardenResponse getGardenResponse;
            getGardenResponse = new GetGardenResponse();
            getGardenResponse.setGardenAddress(garden.getGardenAddress());
            getGardenResponse.setGardenName(garden.getGardenName());
            getGardenResponse.setGardenCreated(garden.getCreatedDate());
            getGardenResponse.setGardenId(garden.getGardenId());
            gardenResponseList.add(getGardenResponse);

        }

        return SuccessResponse.of(gardenResponseList);

    }
    @GetMapping("/get-gardens-users/{gardenId}")
    public SuccessResponse<List<GetUserFromGardenResponse>> getUserFromGarden(@PathVariable Long gardenId) throws Exception {
        return SuccessResponse.of(gardenService.getUserFromGarden(gardenId));

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
    public SuccessResponse<PlantDiseaseDto.PlantDiseaseResponse> detectPlantDisease(PlantDiseaseDto.PlantDiseaseRequest request) throws Exception {

        return SuccessResponse.of(gardenService.plantDiseaseDetection(request.getFile()));
    }
}


