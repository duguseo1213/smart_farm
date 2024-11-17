package com.ssafy.WeCanDoFarm.server.domain.garden.controller;


import com.ssafy.WeCanDoFarm.server.core.annotation.CurrentUser;
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
    public SuccessResponse<List<GetGardenResponse>> getGarden(@CurrentUser User user) throws Exception {
        List<Garden> gardenList = gardenService.getGardens(user.getUsername());
        List<GetGardenResponse> gardenResponseList = new ArrayList<>();

        for(int i=0;i<gardenList.size();i++){

            Garden garden = gardenList.get(i);
            GetGardenResponse getGardenResponse;
            getGardenResponse = new GetGardenResponse();
            getGardenResponse.setGardenAddress(garden.getGardenAddress());
            getGardenResponse.setGardenName(garden.getGardenName());
            getGardenResponse.setGardenCreated(garden.getCreatedDate());
            getGardenResponse.setGardenId(garden.getGardenId());
            getGardenResponse.setGardenImage(garden.getGardenImage());
            gardenResponseList.add(getGardenResponse);

        }

        return SuccessResponse.of(gardenResponseList);

    }
    @GetMapping("/get-gardens-users/{gardenId}")
    public SuccessResponse<List<GetUserFromGardenResponse>> getUserFromGarden(@PathVariable Long gardenId) throws Exception {
        return SuccessResponse.of(gardenService.getUserFromGarden(gardenId));

    }

    @PostMapping("/create-garden")
    public SuccessResponse<String> registerGarden(@RequestBody RegisterGardenRequest request) throws Exception {
        gardenService.registerGarden(request);
        return SuccessResponse.of("텃밭 등록완료");
    }

    @PostMapping("/add-user-to-garden")
    public SuccessResponse<String> registerUserToGarden(@CurrentUser User user,@RequestBody RegisterUserToGardenRequest request) throws Exception {

        gardenService.registerUserToGarden(user,request);
        return SuccessResponse.empty();
    }

    @PostMapping("/plant-disease-detection")
    public SuccessResponse<PlantDiseaseDto.PlantDiseaseResponse> detectPlantDisease(PlantDiseaseDto.PlantDiseaseRequest request) throws Exception {

        return SuccessResponse.of(gardenService.plantDiseaseDetection(request.getFile()));
    }
    @PostMapping("/remote-water")
    public SuccessResponse<String> remoteWater(@CurrentUser User user, @RequestBody FunctionRequest request) throws Exception {
        gardenService.remoteWater(request.getGardenId(), user.getId());
        return SuccessResponse.empty();
    }

    @PostMapping("/take-picture")
    public SuccessResponse<String> takePicture(@CurrentUser User user, @RequestBody FunctionRequest request) throws Exception {

        gardenService.takePicture(request.getGardenId(),user.getId());
        return SuccessResponse.empty();
    }

    @GetMapping("/get-garden-data")
    public SuccessResponse<GetGardenDataResponse> getGardenData(@RequestParam Long gardenId) throws Exception {
        return SuccessResponse.of(gardenService.getGardenStatus(gardenId));
    }

    @PostMapping("/change-garden-name")
    public SuccessResponse<Void> changeGardenName(GardenDto.ChangeGardenNameRequest request) throws Exception {
        gardenService.changeGardenName(request.getGardenId(),request.getGardenName());
        return SuccessResponse.empty();
    }
    
}


