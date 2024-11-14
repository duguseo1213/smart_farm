package com.ssafy.WeCanDoFarm.server.domain.crop.repository;

import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long>, CropQueryRepository {
}
