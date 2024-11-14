package com.ssafy.WeCanDoFarm.server.domain.crop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CropDto {

    @Data
    @AllArgsConstructor
    public static class AddCropRequest{
        private Long gardenId;
        private String crops;
    }

    @Data
    @AllArgsConstructor
    public static class DeleteCropRequest{
        private Long gardenId;
        private String crops;
    }


}
