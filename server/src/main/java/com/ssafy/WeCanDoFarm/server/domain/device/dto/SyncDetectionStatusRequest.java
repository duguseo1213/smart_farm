package com.ssafy.WeCanDoFarm.server.domain.device.dto;

import lombok.Data;

@Data
public class SyncDetectionStatusRequest {
    Long deviceId;
    boolean detectionStatus;

    public boolean getDetectionStatus()
    {
        return this.detectionStatus;
    }

}
