package com.ssafy.WeCanDoFarm.server.domain.crop.service;

import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.crop.constants.CropConst;
import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.dto.CropGrowthStageDto;
import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.Crop;
import com.ssafy.WeCanDoFarm.server.domain.crop.entitiy.CropGrowthStage;
import com.ssafy.WeCanDoFarm.server.domain.crop.repository.CropGrowthStageRepository;
import com.ssafy.WeCanDoFarm.server.domain.crop.repository.CropRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.constants.PlantDiseaseConst;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.PlantDiseaseDto;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.harm.constants.HarmConst;
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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CropService {

    private final CropRepository cropRepository;
    private final GardenRepository gardenRepository;
    private final RestTemplate restTemplate;
    private final CropGrowthStageRepository stageRepository;
    public String lettuceSegment(MultipartFile file) {
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
            responseEntity = restTemplate.exchange(CropConst.PlantDiseaseDetectionURL, HttpMethod.POST, requestEntity,
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }
        log.info(String.valueOf(responseEntity.getBody()));

        return responseEntity.getBody().replaceAll("[\\[\\]\"]", "").trim();
    }

    public void addCrop(CropDto.AddCropRequest request) {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow();
        String[] list = request.getCrops().split("&");
        for(String crop : list) {
            cropRepository.save(Crop.create(garden,crop));

            Crop temp_crop = cropRepository.getCrop(request.getGardenId(), crop);
            stageRepository.save(CropGrowthStage.create(temp_crop,20));
        }
    }



    public void deleteCrop(CropDto.DeleteCropRequest request) {
        String[] list = request.getCrops().split("&");
        for(String crop : list) {
            cropRepository.deleteCropByName(crop);
        }
    }

    public List<String> getCrop(Long gardenId) {
        return cropRepository.getCropNamesByGardenId(gardenId);
    }

    public List<String> recommendCrop(String cropName) {
        return cropRepository.recommandCropByCropName(cropName);
    }

    public void addCropGrowthStage(CropGrowthStageDto.CropGrowthStageRequest request){
        Crop crop = cropRepository.getCrop(request.getGardenId(), request.getCropName());
        int percentage = Integer.parseInt(lettuceSegment(request.getFile()));
        stageRepository.save(CropGrowthStage.create(crop,percentage*20));
    }

    public List<CropGrowthStage> getCropGrowthStage(Long gardenId, String CropName) {
        Crop crop = cropRepository.getCrop(gardenId, CropName);

        return stageRepository.findByCropId(crop.getCropId());
    }
}
