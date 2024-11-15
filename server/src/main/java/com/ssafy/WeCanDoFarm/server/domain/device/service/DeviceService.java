package com.ssafy.WeCanDoFarm.server.domain.device.service;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.dto.RegisterDeviceResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface DeviceService {

    RegisterDeviceResponse registerDevice() throws Exception;

    String getStreamKey(@RequestParam Long gardenId) throws Exception;

}