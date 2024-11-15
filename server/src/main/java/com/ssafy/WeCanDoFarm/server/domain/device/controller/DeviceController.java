package com.ssafy.WeCanDoFarm.server.domain.device.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.dto.RegisterDeviceResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/register")
    public SuccessResponse<RegisterDeviceResponse> registerDevice() throws Exception{
        return SuccessResponse.of(deviceService.registerDevice());
    }
    @GetMapping("/get-stream-key")
    public SuccessResponse<String> getStreamKey(@RequestParam Long gardenId) throws Exception{
        return SuccessResponse.of(deviceService.getStreamKey(gardenId));
    }

}
