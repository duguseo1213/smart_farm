package com.ssafy.WeCanDoFarm.server.domain.timeLapse.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.S3.service.S3UploadService;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.AddTimeLapsePictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto.GetTimeLapsePicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.entitiy.TimeLapse;
import com.ssafy.WeCanDoFarm.server.domain.timeLapse.repository.TimeLapsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeLapseServiceImpl implements TimeLapseService {

    private final TimeLapsRepository timeLapsRepository;
    private final GardenRepository gardenRepository;
    private final S3UploadService s3UploadService;



    @Override
    public void addTimeLapsePicture(AddTimeLapsePictureRequest request) throws Exception {
        String filePath=s3UploadService.upload(request.getImage());
        Garden garden =gardenRepository.findById(request.getGardenId()).orElseThrow();
        TimeLapse tl = TimeLapse.create(garden,filePath);
        timeLapsRepository.save(tl);
    }

    @Override
    public List<GetTimeLapsePicturesResponse> getTimeLapses(Long gardenId) throws Exception {
        List<TimeLapse> timeLapse = timeLapsRepository.findByGardenId(gardenId);
        List<GetTimeLapsePicturesResponse> getTimeLapsePicturesResponses = new ArrayList<>();
        for(TimeLapse tl : timeLapse){
            GetTimeLapsePicturesResponse response = new GetTimeLapsePicturesResponse();
            response.setCreatedDate(tl.getCreatedDate());
            response.setImage(tl.getTimeLapseImage());
            getTimeLapsePicturesResponses.add(response);
        }
        return getTimeLapsePicturesResponses;
    }
}
