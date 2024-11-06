package com.ssafy.WeCanDoFarm.server.domain.harm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class HarmPictureDto {

    @Data
    public static class HarmDetectionRequest{
        private MultipartFile file;

        public static HarmDetectionRequest of(MultipartFile file){
            HarmDetectionRequest harmDetectionRequest = new HarmDetectionRequest();
            harmDetectionRequest.setFile(file);
            return harmDetectionRequest;
        }
    }

    @Data
    public static class HarmDetectionResponse{
        private Boolean isHarm;
        private String HarmAnimalType;

        public static HarmDetectionResponse of(String HarmAnimalType){
            HarmDetectionResponse harmDetectionResponse = new HarmDetectionResponse();
            harmDetectionResponse.isHarm = !HarmAnimalType.equals("none");
            harmDetectionResponse.HarmAnimalType = HarmAnimalType;
            return harmDetectionResponse;
        }
    }
}
