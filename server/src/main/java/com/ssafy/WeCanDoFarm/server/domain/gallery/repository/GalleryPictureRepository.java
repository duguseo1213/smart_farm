package com.ssafy.WeCanDoFarm.server.domain.gallery.repository;

import com.ssafy.WeCanDoFarm.server.domain.gallery.entity.GalleryPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GalleryPictureRepository extends JpaRepository<GalleryPicture, Long> {
    @Query("SELECT g FROM GalleryPicture g WHERE g.garden.gardenId = :gardenId")
    List<GalleryPicture> findByGardenId(@Param("gardenId") Long gardenId);
    @Query("SELECT g FROM GalleryPicture g WHERE g.garden.gardenId = :gardenId AND FUNCTION('DATE', g.garden.createdDate) = FUNCTION('DATE', :date)")
    List<GalleryPicture> findByGardenIdOnDate(@Param("gardenId") Long gardenId, @Param("date") Date date);
}
