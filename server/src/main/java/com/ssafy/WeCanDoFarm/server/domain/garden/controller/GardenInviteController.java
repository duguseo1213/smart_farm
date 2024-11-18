package com.ssafy.WeCanDoFarm.server.domain.garden.controller;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenUserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("/api/invite")
public class GardenInviteController {

    @GetMapping("/redirect")
    public ModelAndView redirectToApp(@RequestParam("GardenId") Long gardenId,
                                      @RequestParam("userType") GardenUserType userType) {
        // 쿼리 파라미터로 추가할 URL 생성

        String appUrl = "myapp://somepath?GardenId=" + gardenId + "&userType=" + userType;
        log.info(appUrl);
        // 리다이렉트
        return new ModelAndView("redirect:" + appUrl);
    }
}
