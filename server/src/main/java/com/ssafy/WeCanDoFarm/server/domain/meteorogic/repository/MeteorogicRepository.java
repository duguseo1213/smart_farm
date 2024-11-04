package com.ssafy.WeCanDoFarm.server.domain.meteorogic.repository;

import com.ssafy.WeCanDoFarm.server.domain.meteorogic.entity.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeteorogicRepository extends JpaRepository<AreaCode,Long> {
}
