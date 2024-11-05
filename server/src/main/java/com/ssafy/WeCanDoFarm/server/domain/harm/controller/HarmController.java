package com.ssafy.WeCanDoFarm.server.domain.harm.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.service.HarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/harm")
@Controller
public class HarmController {
    private final HarmService harmService;
    @PostMapping("/add-harm-picture")
    SuccessResponse<String> addHarmPicture(@RequestBody AddHarmPictureRequest request) throws Exception{
        harmService.addHarmPicture(request);
        return SuccessResponse.empty();
    }
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


}
