package com.ssafy.WeCanDoFarm.server.domain.crop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Data
    @AllArgsConstructor
    public static class GetCropRequest{
        private Long gardenId;
    }

    @Data
    @NoArgsConstructor
    public static class GetCropResponse{
        private String cropName;
        private int growthPercentage;

        public static GetCropResponse create(String cropName, int growthPercentage){
            GetCropResponse getCropResponse = new GetCropResponse();
            getCropResponse.setCropName(cropName);
            getCropResponse.setGrowthPercentage(growthPercentage);
            return getCropResponse;
        }
    }

}
