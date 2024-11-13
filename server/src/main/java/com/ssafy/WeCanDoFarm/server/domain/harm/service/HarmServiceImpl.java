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
import com.ssafy.WeCanDoFarm.server.domain.harm.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmPicture;
import com.ssafy.WeCanDoFarm.server.domain.harm.entity.HarmVideo;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmPictureRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.repository.HarmVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HarmServiceImpl implements HarmService {

    private final RestTemplate restTemplate;
    private final HarmPictureRepository HarmPictureRepository;
    private final HarmVideoRepository HarmVideoRepository;
    private final GardenRepository gardenRepository;
    private final S3UploadService s3UploadService;

    public String doHarmAnimalDetection(MultipartFile file) {
        ResponseEntity<String> responseEntity;
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
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }
        log.info(String.valueOf(responseEntity.getBody()));

        return responseEntity.getBody().replaceAll("[\\[\\]\"]", "").trim();
    }

    @Override
    public Long addHarmPicture(Long deviceId, MultipartFile file,String animalType) throws Exception {
        String filePath = s3UploadService.upload(file);
        HarmPicture harmPicture = HarmPicture.crate(gardenRepository.getGarden(deviceId),filePath,animalType);
        return HarmPictureRepository.save(harmPicture).getHarmPictureId();
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
    public String detectionHarmAnimal(MultipartFile file) {
        return doHarmAnimalDetection(file);
    }

    @Override
    public List<GetHarmAnimalPictureResponse> getHarmAnimalPictures(Long gardenId) throws Exception {
        List<HarmPicture> list = HarmPictureRepository.findByNotType(gardenId,"human");
        List<GetHarmAnimalPictureResponse> responses = new ArrayList<>();
        for(HarmPicture hp : list){
            GetHarmAnimalPictureResponse response = new GetHarmAnimalPictureResponse();
            response.setHarmPictureId(hp.getHarmPictureId());
            response.setAnimalType(hp.getHarmTarget());
            response.setImage(hp.getHarmPicture());
            response.setCreatedDate(hp.getCreatedDate());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<GetHarmHumanPictureResponse> getHarmHumanPictures(Long gardenId) throws Exception {
        List<HarmPicture> list = HarmPictureRepository.findByType(gardenId,"human");
        List<GetHarmHumanPictureResponse> responses = new ArrayList<>();
        for(HarmPicture hp : list){
            GetHarmHumanPictureResponse response = new GetHarmHumanPictureResponse();
            response.setHarmPictureId(hp.getHarmPictureId());
            response.setImage(hp.getHarmPicture());
            response.setCreatedDate(hp.getCreatedDate());
            responses.add(response);
        }
        return responses;
    }

}
