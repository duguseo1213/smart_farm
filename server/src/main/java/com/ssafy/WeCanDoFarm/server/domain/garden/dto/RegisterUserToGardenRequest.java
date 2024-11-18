package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenUserType;
import lombok.Data;

@Data
public class RegisterUserToGardenRequest {
    private long gardenId;
    private GardenUserType userType;
}
