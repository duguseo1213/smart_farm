package com.ssafy.WeCanDoFarm.server.domain.harm.service;

import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.HarmPictureDto;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HarmService {
    void addHarmPicture(AddHarmPictureRequest request) throws Exception;
    void addHarmVideo(AddHarmVideoRequest request) throws Exception;
    List<HarmPicture> getHarmPictures(Long gardenId) throws Exception;
    String getHarmVideo(Long harmPictureId) throws Exception;
    HarmPictureDto.HarmDetectionResponse detectionHarmAnimal(MultipartFile file);
}
