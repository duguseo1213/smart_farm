package com.ssafy.WeCanDoFarm.server.domain.harm.controller;

import com.ssafy.WeCanDoFarm.server.core.annotation.CurrentUser;
import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.service.HarmService;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
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

//    @PostMapping("/add-harm-video")
//    SuccessResponse<String> addHarmVideo(@RequestBody AddHarmVideoRequest request) throws Exception {
//        harmService.addHarmVideo(request);
//        return SuccessResponse.empty();
//    }
    @GetMapping("/get-harm-picture")
    SuccessResponse<List<HarmPicture>> getHarmPictures(@RequestParam Long gardenId) throws Exception
    {

        return SuccessResponse.of(harmService.getHarmPictures(gardenId));
    }

    @GetMapping("/get-harm-animal")
    SuccessResponse<List<GetHarmAnimalPictureResponse>> getHarmAnimalPictures(@RequestParam Long gardenId) throws Exception
    {

        return SuccessResponse.of(harmService.getHarmAnimalPictures(gardenId));
    }
    @GetMapping("/get-harm-human")
    SuccessResponse<List<GetHarmHumanPictureResponse>> getHarmHumanPictures(@RequestParam Long gardenId) throws Exception
    {
        return SuccessResponse.of(harmService.getHarmHumanPictures(gardenId));
    }
    @GetMapping("/get-harm-Video")
    SuccessResponse<String> getHarmVideo(@RequestParam Long harmPictureId) throws Exception
    {
        return SuccessResponse.of(harmService.getHarmVideo(harmPictureId));
    }
    @GetMapping("/get-detection_status")
    SuccessResponse<Boolean> getDetectionStatus(@RequestParam Long gardenId) throws Exception
    {
        return SuccessResponse.of(harmService.getDetectionStatus(gardenId));
    }
    @PostMapping("/toggle-detection")
    SuccessResponse<String> toggleDetection(@CurrentUser User user, @RequestParam Long gardenId) throws Exception
    {
        harmService.toggleDetection(user.getId(),gardenId);
        return SuccessResponse.empty();
    }


}
