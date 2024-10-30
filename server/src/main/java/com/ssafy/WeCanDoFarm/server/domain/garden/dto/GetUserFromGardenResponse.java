package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenUserType;
import lombok.Data;

@Data
public class GetUserFromGardenResponse {
    String userName;
    String userNickname;
    GardenUserType gardenUserType;
}
