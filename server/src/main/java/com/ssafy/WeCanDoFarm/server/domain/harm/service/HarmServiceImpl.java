package com.ssafy.WeCanDoFarm.server.domain.harm.service;

import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.S3.service.S3UploadService;
import com.ssafy.WeCanDoFarm.server.domain.garden.constants.PlantDiseaseConst;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.PlantDiseaseDto;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.constants.HarmConst;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.AddHarmVideoRequest;
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.HarmPictureDto;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmPictureRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class HarmServiceImpl implements HarmService {

    private final RestTemplate restTemplate;
    private final HarmPictureRepository HarmPictureRepository;
    private final HarmVideoRepository HarmVideoRepository;
    private final GardenRepository gardenRepository;
    private final S3UploadService s3UploadService;

    public HarmPictureDto.HarmDetectionResponse doHarmAnimalDetection(MultipartFile file) {
        ResponseEntity<HarmPictureDto.HarmDetectionResponse> responseEntity;
        try {
            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);

            // MultiValueMap을 사용하여 요청 본문 생성
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("imageFile", new org.springframework.core.io.ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // 파일 이름 설정
                }
            });

            // 요청 본문 생성
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.exchange(HarmConst.HarmDetectionURL, HttpMethod.POST, requestEntity,
                    HarmPictureDto.HarmDetectionResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }
        return HarmPictureDto.HarmDetectionResponse.of(responseEntity.getBody().getHarmAnimalType());
    }

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

    @Override
    public HarmPictureDto.HarmDetectionResponse detectionHarmAnimal(MultipartFile file) {
        HarmPictureDto.HarmDetectionResponse response = doHarmAnimalDetection(file);
        if(response.getIsHarm()){
            String filePath = s3UploadService.upload(request.getFile());
            HarmPicture harmPicture = HarmPicture.crate(gardenRepository.findById(request.getGardenId()).orElseThrow(),filePath,null);
            HarmPictureRepository.save(harmPicture);
        }
        return response;
    }
}
