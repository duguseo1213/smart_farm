package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;

import java.sql.SQLException;
import java.util.List;

public interface GardenQueryRepository {
    List<Garden> getGardens(String username) throws SQLException;

    Garden getGarden(Long deviceId) throws SQLException;
}
