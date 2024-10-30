package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;

import java.util.List;

public interface GardenQueryRepository {
    List<Garden> getGardens(String username);
}
