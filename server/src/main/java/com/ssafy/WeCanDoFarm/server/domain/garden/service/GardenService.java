package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import com.ssafy.WeCanDoFarm.server.domain.mqtt.handler.GardenDataMessage;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GardenService{

    List<Garden> getGardens(String username) throws Exception;
    void registerGarden(RegisterGardenRequest request) throws Exception;
    void registerUserToGarden(User user, RegisterUserToGardenRequest request) throws Exception;
    List<GetUserFromGardenResponse> getUserFromGarden(Long gardenId) throws Exception;
    PlantDiseaseDto.PlantDiseaseResponse plantDiseaseDetection(MultipartFile file) throws Exception;
    void addGardenData(Long deviceId, GardenDataMessage message) throws Exception;
    void remoteWater(Long gardenId, Long userId) throws Exception;
    void takePicture(Long gardenId, Long userId) throws Exception;
    List<GetGardenDataResponse> getGardenStatus(Long gardenId) throws Exception;

}
