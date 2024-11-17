package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import lombok.Data;

public class GardenDto {

    @Data
    public static class ChangeGardenNameRequest {
        private String gardenName;
        private Long gardenId;
    }
}
