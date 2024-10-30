package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetGardenResponse {
    Long GardenId;
    String gardenName;
    String gardenAddress;
    Date gardenCreated;
}
