package com.ssafy.WeCanDoFarm.server.domain.harm.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.HarmPictureDto;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.service.HarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/harm")
@RestController
public class HarmController {
    private final HarmService harmService;

    @PostMapping("/add-harm-video")
    SuccessResponse<String> addHarmVideo(@RequestBody AddHarmVideoRequest request) throws Exception {
        harmService.addHarmVideo(request);
        return SuccessResponse.empty();
    }
    @GetMapping("/get-harm-picture")
    SuccessResponse<List<HarmPicture>> getHarmPictures(@RequestParam Long gardenId) throws Exception
    {

        return SuccessResponse.of(harmService.getHarmPictures(gardenId));
    }
    @GetMapping("/get-harm-Video")
    SuccessResponse<String> getHarmVideo(@RequestParam Long harmPictureId) throws Exception
    {
        return SuccessResponse.of(harmService.getHarmVideo(harmPictureId));
    }

    @PostMapping("/detection-harm-animal")
    SuccessResponse<HarmPictureDto.HarmDetectionResponse> detectionHarmAnimal(HarmPictureDto.HarmDetectionRequest request) throws Exception
    {   String animalType = harmService.detectionHarmAnimal(request.getFile());
        Long id = 0L;
        if(!animalType.equals("none")){
            id = harmService.addHarmPicture(request.getDeviceId(),request.getFile());
        }
        return SuccessResponse.of(HarmPictureDto.HarmDetectionResponse.of(animalType,id));
    }
}
