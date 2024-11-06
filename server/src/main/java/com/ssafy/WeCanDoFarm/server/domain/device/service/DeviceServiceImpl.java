package com.ssafy.WeCanDoFarm.server.domain.device.service;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.dto.RegisterDeviceResponse;
import com.ssafy.WeCanDoFarm.server.domain.device.entity.Device;
import com.ssafy.WeCanDoFarm.server.domain.device.entity.DeviceStatus;
import com.ssafy.WeCanDoFarm.server.domain.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public RegisterDeviceResponse registerDevice() throws Exception {
        RegisterDeviceResponse response = new RegisterDeviceResponse();
        Device device = Device.create(DeviceStatus.AVAILABLE, UUID.randomUUID().toString(),new Date());
        deviceRepository.save(device);
        response.setStreamKey(device.getStreamKey());
        response.setDeviceId(device.getDeviceId());
        return response;
    }
}
