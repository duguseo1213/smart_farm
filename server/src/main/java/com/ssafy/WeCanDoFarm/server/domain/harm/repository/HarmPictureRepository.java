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
}
