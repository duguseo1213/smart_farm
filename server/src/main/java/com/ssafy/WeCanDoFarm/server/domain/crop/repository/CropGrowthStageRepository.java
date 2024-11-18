package com.ssafy.WeCanDoFarm.server.domain.crop.repository;

import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.CropGrowthStage;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CropGrowthStageRepository extends JpaRepository<CropGrowthStage, Integer> {

    @Query("SELECT c FROM CropGrowthStage c WHERE c.crop.cropId = :cropId")
    List<CropGrowthStage> findByCropId(@Param("cropId") Long cropId);

    @Query("SELECT c.growthPercentage FROM CropGrowthStage c WHERE c.crop.cropId = :cropId ORDER BY c.date ASC")
    int findEarliestByCropId(@Param("cropId") Long cropId);
}
