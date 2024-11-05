package com.ssafy.WeCanDoFarm.server.domain.timeLapse.service;

import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.AddTimeLapsePictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.GetTimeLapsePicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TimeLapseService {
    void addTimeLapsePicture(AddTimeLapsePictureRequest request) throws Exception;
    List<GetTimeLapsePicturesResponse> getTimeLapses(Long gardenId)throws Exception;

}
