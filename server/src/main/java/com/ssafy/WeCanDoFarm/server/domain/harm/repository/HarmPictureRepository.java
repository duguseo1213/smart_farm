package com.ssafy.WeCanDoFarm.server.domain.harm.repository;

import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HarmPictureRepository extends JpaRepository<HarmPicture, Long> {
    @Query("SELECT h FROM HarmPicture h WHERE h.garden.gardenId = :gardenId")
    List<HarmPicture> findByGardenId(@Param("gardenId") Long gardenId);
    @Query("SELECT h FROM HarmPicture h WHERE h.garden.gardenId = :gardenId and h.harmTarget = :type")
    List<HarmPicture> findByType(@Param("gardenId") Long gardenId, @Param("type") String type);
    @Query("SELECT h FROM HarmPicture h WHERE h.garden.gardenId = :gardenId and h.harmTarget != :type and h.harmTarget is not null")
    List<HarmPicture> findByNotType(@Param("gardenId") Long gardenId, @Param("type") String type);
}
