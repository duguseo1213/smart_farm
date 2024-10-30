package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GardenRepository extends JpaRepository<Garden, Long>, GardenQueryRepository {
}
