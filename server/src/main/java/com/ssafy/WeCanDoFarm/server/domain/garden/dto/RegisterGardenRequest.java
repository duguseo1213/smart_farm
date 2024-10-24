package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import lombok.Data;

@Data
public class RegisterGardenRequest {
    String gardenName;
    String username;
    String gardenAddress;
    long deviceSN;

}
