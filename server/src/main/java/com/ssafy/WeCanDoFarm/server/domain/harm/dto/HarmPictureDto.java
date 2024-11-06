package com.ssafy.WeCanDoFarm.server.domain.harm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class HarmPictureDto {

    @Data
    public static class HarmDetectionRequest{
        private MultipartFile file;
        private Long deviceId;

        public static HarmDetectionRequest of(MultipartFile file, Long deviceId){
            HarmDetectionRequest harmDetectionRequest = new HarmDetectionRequest();
            harmDetectionRequest.setFile(file);
            harmDetectionRequest.setDeviceId(deviceId);
            return harmDetectionRequest;
        }
    }

    @Data
    public static class HarmDetectionResponse{
        private Boolean isHarm;
        private String harmAnimalType;
        private Long harmPictureId;

        public static HarmDetectionResponse of(String HarmAnimalType, Long HarmPictureId){
            HarmDetectionResponse harmDetectionResponse = new HarmDetectionResponse();
            harmDetectionResponse.isHarm = !HarmAnimalType.equals("none");
            harmDetectionResponse.harmAnimalType = HarmAnimalType;
            harmDetectionResponse.harmPictureId = HarmPictureId;
            return harmDetectionResponse;
        }

        public static HarmDetectionResponse of(String HarmAnimalType){
            HarmDetectionResponse harmDetectionResponse = new HarmDetectionResponse();
            harmDetectionResponse.isHarm = !HarmAnimalType.equals("none");
            harmDetectionResponse.harmAnimalType = HarmAnimalType;
            harmDetectionResponse.harmPictureId = 0L;
            return harmDetectionResponse;
        }
    }
}
