package com.ssafy.WeCanDoFarm.server.domain.garden.controller;


import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/garden")
@RequiredArgsConstructor
public class GardenController {

    @GetMapping("/")
    public SuccessResponse<List<Garden>> getGardenList(){



        return SuccessResponse.of(new ArrayList<>());

    }
    @PostMapping("/")
    public SuccessResponse<?> registerGarden(Garden garden){


        return SuccessResponse.of("텃밭 등록완료");
    }
    
}
