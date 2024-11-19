package com.ssafy.WeCanDoFarm.server.domain.device.service;

import com.ssafy.WeCanDoFarm.server.domain.device.dto.RegisterDeviceResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.dto.SyncDetectionStatusRequest;
import org.springframework.web.bind.annotation.RequestParam;

public interface DeviceService {

    RegisterDeviceResponse registerDevice() throws Exception;
    String getStreamKey(Long gardenId) throws Exception;
    void syncDetectionStatus(SyncDetectionStatusRequest request) throws Exception;

}