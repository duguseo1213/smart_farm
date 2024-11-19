package com.ssafy.WeCanDoFarm.server.domain.crop.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class CropGrowthStageDto {

    @Data
    public static class CropGrowthStageRequest{
        private Long gardenId;
        private String cropName;
        private MultipartFile file;
    }

    @Data
    public static class getCropGrowthsStageRequest{
        private Long gardenId;
        private String cropName;
    }
}
