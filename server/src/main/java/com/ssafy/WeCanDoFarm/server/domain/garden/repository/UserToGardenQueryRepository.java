package com.ssafy.WeCanDoFarm.server.domain.garden.repository;

import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

public interface UserToGardenQueryRepository {
    List<UserToGarden> findByGardenId(@Param("gardenId") Long gardenId);
}
