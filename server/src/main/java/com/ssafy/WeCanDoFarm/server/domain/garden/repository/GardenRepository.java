package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GardenRepository extends JpaRepository<Garden, Long>, GardenQueryRepository {

    @Query("SELECT u FROM UserToGarden u WHERE u.garden.gardenId = :gardenId")
    List<UserToGarden> findByGardenId(@Param("gardenId") Long gardenId);
}
