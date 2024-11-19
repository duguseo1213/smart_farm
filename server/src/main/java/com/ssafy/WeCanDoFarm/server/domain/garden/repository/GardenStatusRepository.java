package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenStatusRepository extends JpaRepository<GardenStatus, Long> {

}
