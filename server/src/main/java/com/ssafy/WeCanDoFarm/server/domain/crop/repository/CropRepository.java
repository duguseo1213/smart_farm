package com.ssafy.WeCanDoFarm.server.domain.crop.repository;

import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long>, CropQueryRepository {

}
