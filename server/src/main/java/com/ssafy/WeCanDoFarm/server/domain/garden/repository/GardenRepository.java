package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface GardenRepository extends JpaRepository<Garden, Long>, GardenQueryRepository {

    @Query("SELECT u FROM UserToGarden u WHERE u.garden.gardenId = :gardenId")
    List<UserToGarden> findByGardenId(@Param("gardenId") Long gardenId);

    @Query("SELECT gs FROM GardenStatus gs WHERE gs.garden.gardenId = :gardenId AND gs.createdDate >= :sevenDaysAgo")
    List<GardenStatus> findGardenStatusFromLastWeek(@Param("gardenId") Long gardenId, @Param("sevenDaysAgo") LocalDate sevenDaysAgo);
}
