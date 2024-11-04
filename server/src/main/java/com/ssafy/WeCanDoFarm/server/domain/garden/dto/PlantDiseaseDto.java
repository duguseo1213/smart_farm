package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


public class PlantDiseaseDto{
    @Data
    public static class PlantDiseaseResponse {
        private boolean isDiseased;
        private String diseaseName;
        private String diseaseSolvent;

        public static PlantDiseaseResponse of(String diseaseName, String diseaseSolvent ){
            PlantDiseaseResponse response = new PlantDiseaseResponse();
            response.setDiseased(!diseaseName.equals("none"));
            response.setDiseaseName(diseaseName);
            response.setDiseaseSolvent(diseaseSolvent);
            return response;
        }

        public static PlantDiseaseResponse empty(){
            PlantDiseaseResponse response = new PlantDiseaseResponse();
            response.setDiseased(true);
            response.setDiseaseName("test1");
            response.setDiseaseSolvent("test2");
            return response;
        }
    }

    @Data
    public static class PlantDiseaseRequest{
        private MultipartFile file;

        public static PlantDiseaseRequest of(MultipartFile file){
            PlantDiseaseRequest request = new PlantDiseaseRequest();
            request.setFile(file);
            return request;
        }
    }
}

