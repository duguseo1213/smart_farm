package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;

import java.sql.SQLException;
import java.util.List;

public interface GardenRepository {
    public List<Garden> getGardens(int userId) throws SQLException;
    public void registerGarden(Garden garden) throws SQLException;
}
