package com.ssafy.WeCanDoFarm.server.domain.harm.service;

import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.HarmPictureDto;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HarmService {
    Long addHarmPicture(Long deviceId, MultipartFile file) throws Exception;
    void addHarmVideo(AddHarmVideoRequest request) throws Exception;
    List<HarmPicture> getHarmPictures(Long gardenId) throws Exception;
    String getHarmVideo(Long harmPictureId) throws Exception;
    String detectionHarmAnimal(MultipartFile file);
}
