package com.ssafy.WeCanDoFarm.server.domain.timeLapse.service;

import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.AddTimeLapsePictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.GetTimeLapsePicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TimeLapseService {
    String upload(MultipartFile image) throws Exception;
    String uploadImage(MultipartFile image) throws Exception;
    void validateImageFileExtention(String filename) throws Exception;
    String uploadImageToS3(MultipartFile image) throws Exception;
    void addTimeLapsePicture(AddTimeLapsePictureRequest request) throws Exception;
    List<GetTimeLapsePicturesResponse> getTimeLapses(Long gardenId)throws Exception;

}
