package com.ssafy.WeCanDoFarm.server.domain.timeLapse.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.AddTimeLapsePictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.GetTimeLapsePicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.service.TimeLapseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/timeLapse")
@RequiredArgsConstructor
public class TimeLapseController {

    private final TimeLapseService timeLapseService;
    @PostMapping("/add")
    SuccessResponse<String> addTimeLapsePicture(@ModelAttribute AddTimeLapsePictureRequest request) throws Exception {
        timeLapseService.addTimeLapsePicture(request);
        return SuccessResponse.of("타임랩스 이미지 등록완료");
    }

//    SuccessResponse<String> getTimeLapsePictures(){
//
//    }
    @GetMapping("/get-list")
    SuccessResponse<List<GetTimeLapsePicturesResponse>> getTimeLapsePictures(@RequestParam Long gardenId) throws Exception {
        return SuccessResponse.of(timeLapseService.getTimeLapses(gardenId));
    }



}
