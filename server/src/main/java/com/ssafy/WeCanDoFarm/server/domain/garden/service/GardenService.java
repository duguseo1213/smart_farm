package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterUserToGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GardenService {
    public List<Garden> getGardens(String username) throws Exception;
    public void registerGarden(RegisterGardenRequest request) throws Exception;
    public void registerUserToGarden(RegisterUserToGardenRequest request) throws Exception;
}
