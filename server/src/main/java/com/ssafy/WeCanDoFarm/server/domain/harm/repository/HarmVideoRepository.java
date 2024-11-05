package com.ssafy.WeCanDoFarm.server.domain.harm.repository;

import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HarmVideoRepository extends JpaRepository<HarmVideo, Long> {
    @Query("SELECT h FROM HarmVideo h WHERE h.harmPicture.harmPictureId = :harmPictureId")
    HarmPicture findByHarmPictureId(@Param("harmPictureId") Long harmPictureId);
}
