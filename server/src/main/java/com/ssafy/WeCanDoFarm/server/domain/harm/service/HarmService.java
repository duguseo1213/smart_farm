package com.ssafy.WeCanDoFarm.server.domain.harm.service;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HarmService {
    Long addHarmPicture(Long deviceId, MultipartFile file,String animalType) throws Exception;
    void addHarmVideo(AddHarmVideoRequest request) throws Exception;
    List<HarmPicture> getHarmPictures(Long gardenId) throws Exception;
    String getHarmVideo(Long harmPictureId) throws Exception;
    String detectionHarmAnimal(MultipartFile file);
    List<GetHarmAnimalPictureResponse> getHarmAnimalPictures(Long gardenId) throws Exception;
    List<GetHarmHumanPictureResponse> getHarmHumanPictures(Long gardenId) throws Exception;
}
