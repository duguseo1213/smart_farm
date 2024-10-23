package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;

import java.util.List;

public interface GardenService {
    public List<Garden> getGardens(int userId) throws Exception;
    public void registerGarden(Garden garden) throws Exception;
}
