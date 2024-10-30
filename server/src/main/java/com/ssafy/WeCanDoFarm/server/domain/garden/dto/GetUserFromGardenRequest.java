package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import lombok.Data;

@Data
public class GetUserFromGardenRequest {
    String userName;
    Long gardenId;

}
