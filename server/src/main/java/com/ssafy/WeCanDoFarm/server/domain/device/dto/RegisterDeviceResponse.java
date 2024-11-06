package com.ssafy.WeCanDoFarm.server.domain.device.dto;

import lombok.Data;

@Data
public class RegisterDeviceResponse {
    Long deviceId;
    String streamKey;
}
