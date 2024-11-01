package com.ssafy.WeCanDoFarm.server.domain.meteorogic.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.meteorogic.dto.AreaCodeDto;
import com.ssafy.WeCanDoFarm.server.domain.meteorogic.service.MeteorogicalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/meteo")
@Tag(name = "기상청 관련 API")
public class MeteorogicalController {

    private final MeteorogicalService meteorogicalService;

    @GetMapping("/conversionareacode")
    public SuccessResponse<AreaCodeDto.AreaCodeResponse> checkCoordinates(@RequestParam String latitude, @RequestParam String longitude) {
        // 위도와 경도를 사용하여 주소를 가져옴
//        String Address = meteorogicalService.conversionToAddress(latitude,longitude);
//        log.info("checkAdress : {}",Address);
//        // 주소를 기반으로 코드 출력
        return SuccessResponse.of(AreaCodeDto.AreaCodeResponse.of(longitude,latitude,meteorogicalService.getAreaCode(meteorogicalService.conversionToAddress(latitude,longitude))));
    }
}
