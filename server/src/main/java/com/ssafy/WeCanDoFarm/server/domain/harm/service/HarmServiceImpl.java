package com.ssafy.WeCanDoFarm.server.domain.harm.service;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.S3.service.S3UploadService;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmPictureRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class HarmServiceImpl implements HarmService {

    private final HarmPictureRepository HarmPictureRepository;
    private final HarmVideoRepository HarmVideoRepository;
    private final GardenRepository gardenRepository;
    private final S3UploadService s3UploadService;

    @Override
    public void addHarmPicture(AddHarmPictureRequest request) throws Exception {
        String filePath = s3UploadService.upload(request.getFile());
        HarmPicture harmPicture = HarmPicture.crate(gardenRepository.findById(request.getGardenId()).orElseThrow(),filePath,null);
        HarmPictureRepository.save(harmPicture);

    }

    @Override
    public void addHarmVideo(AddHarmVideoRequest request) throws Exception {
        String filePath = s3UploadService.upload(request.getFile());
        HarmPicture harmPicture = HarmPictureRepository.findById(request.getHarmPictureId()).orElseThrow();
        HarmVideo harmVideo =  HarmVideo.create(harmPicture,gardenRepository.findById(request.getGardenId()).orElseThrow(),filePath);
        HarmVideoRepository.save(harmVideo);
    }

    @Override
    public List<HarmPicture> getHarmPictures(Long gardenId) throws Exception {
        return HarmPictureRepository.findByGardenId(gardenId);
    }

    @Override
    public String getHarmVideo(Long harmPictureId) throws Exception {
        return HarmVideoRepository.findByHarmPictureId(harmPictureId).getHarmPicture();
    }
}
