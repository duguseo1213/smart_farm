package com.ssafy.WeCanDoFarm.server.domain.harm.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.HarmPictureDto;
import com.ssafy.WeCanDoFarm.server.domain.harm.service.HarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/harm")
@RestController
public class HarmIoTController {

    private final HarmService harmService;

    @PostMapping("/detection-harm-animal")
    SuccessResponse<HarmPictureDto.HarmDetectionResponse> detectionHarmAnimal(HarmPictureDto.HarmDetectionRequest request) throws Exception
    {   String animalType = harmService.detectionHarmAnimal(request.getFile());
        Long id = 0L;
        if(!animalType.equals("none")){
            id = harmService.addHarmPicture(request.getDeviceId(),request.getFile(),animalType);
        }
        return SuccessResponse.of(HarmPictureDto.HarmDetectionResponse.of(animalType,id));
    }
}
