package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.PlantDiseaseDto;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterUserToGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GardenService{
    List<Garden> getGardens(String username) throws Exception;
    void registerGarden(RegisterGardenRequest request) throws Exception;
    void registerUserToGarden(RegisterUserToGardenRequest request) throws Exception;
    List<GetUserFromGardenResponse> getUserFromGarden(Long gardenId) throws Exception;
    PlantDiseaseDto.PlantDiseaseResponse plantDiseaseDetection(MultipartFile file) throws Exception;
}
