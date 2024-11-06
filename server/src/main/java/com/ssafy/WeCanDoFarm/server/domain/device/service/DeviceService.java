package com.ssafy.WeCanDoFarm.server.domain.device.service;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.dto.RegisterDeviceResponse;

public interface DeviceService {

    RegisterDeviceResponse registerDevice() throws Exception;
}
