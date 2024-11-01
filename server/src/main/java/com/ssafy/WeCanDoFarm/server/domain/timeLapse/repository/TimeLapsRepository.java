package com.ssafy.WeCanDoFarm.server.domain.timeLapse.repository;

import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeLapsRepository extends JpaRepository<TimeLapse, Long> {

    @Query("SELECT t FROM TimeLapse t WHERE t.garden.gardenId = :gardenId")
    List<TimeLapse> findByGardenId(@Param("gardenId") Long gardenId);
}
