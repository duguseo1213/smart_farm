package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.core.configuration.MqttConfig;
import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.device.entity.Device;
import com.ssafy.WeCanDoFarm.server.domain.device.entity.DeviceStatus;
import com.ssafy.WeCanDoFarm.server.domain.device.repository.DeviceRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.constants.PlantDiseaseConst;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.*;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenStatus;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenUserType;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenStatusRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.UserToGardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.mqtt.handler.FunctionMessage;
import com.ssafy.WeCanDoFarm.server.domain.mqtt.handler.GardenDataMessage;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GardenServiceImpl implements GardenService {

    private final RestTemplate restTemplate;
    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final UserToGardenRepository userToGardenRepository;
    private final DeviceRepository deviceRepository;
    private final GardenStatusRepository gardenStatusRepository;
    private final MqttConfig.MqttOutboundGateway mqttOutboundGateway;

    public PlantDiseaseDto.PlantDiseaseResponse doPlantDiseaseDetection(MultipartFile file) {
        ResponseEntity<PlantDiseaseDto.PlantDiseaseResponse> responseEntity;
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
            responseEntity = restTemplate.exchange(PlantDiseaseConst.PlantDiseaseDetectionURL, HttpMethod.POST, requestEntity,
                    PlantDiseaseDto.PlantDiseaseResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }
        return PlantDiseaseDto.PlantDiseaseResponse.of(responseEntity.getBody().getDiseaseName(),responseEntity.getBody().getDiseaseSolvent());
    }




    @Override
    public List<Garden> getGardens(String username) throws Exception {
        return gardenRepository.getGardens(username);
    }

    @Override
    public void registerGarden(RegisterGardenRequest request) throws Exception {
        Device device =  deviceRepository.findFirstByDeviceStatus(DeviceStatus.AVAILABLE);
        if(device == null) throw new BaseException(ErrorCode.NO_AVAILABLE_DEVICE);
        Garden garden = Garden.create(request.getGardenName(), "", request.getGardenAddress(),request.getCrop(),device);
        deviceRepository.updateDeviceStatusById(device.getDeviceId(), DeviceStatus.UNAVAILABLE);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        UserToGarden userToGarden = UserToGarden.create(user, garden, GardenUserType.LEADER);
        gardenRepository.save(garden);
        userToGardenRepository.save(userToGarden);
    }

    @Override
    public void registerUserToGarden(User user,RegisterUserToGardenRequest request) throws Exception {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow();
        log.info(user.toString());
        UserToGarden userToGarden = UserToGarden.create(user, garden,GardenUserType.MEMBER);
        userToGardenRepository.save(userToGarden);

    }

    @Override
    public List<GetUserFromGardenResponse> getUserFromGarden(Long gardenId) throws Exception {
        List<UserToGarden> userToGardens = userToGardenRepository.findByGardenId(gardenId);

        List<GetUserFromGardenResponse> responses = new ArrayList<>();
        for(int i=0;i<userToGardens.size();i++){
            GetUserFromGardenResponse response = new GetUserFromGardenResponse();
            UserToGarden gardenUser = userToGardens.get(i);
            response.setGardenUserType(gardenUser.getGardenUserType());
            response.setUserNickname(gardenUser.getUser().getNickname());
            response.setUserName(gardenUser.getUser().getUsername());
            responses.add(response);
        }


        return responses;
    }

    @Override
    public PlantDiseaseDto.PlantDiseaseResponse plantDiseaseDetection(MultipartFile file) throws Exception {

        return doPlantDiseaseDetection(file);
    }

    @Override
    public void addGardenData(Long deviceId, GardenDataMessage message) throws Exception {
        Garden garden = gardenRepository.getGarden(deviceId);
        GardenStatus gardenStatus = GardenStatus.create(garden,message.getHumidity(),message.getIlluminance(),message.getSoil_moisture(),message.getTemperature(),new Date());
        gardenStatusRepository.save(gardenStatus);
    }

    @Override
    public void remoteWater(Long gardenId, Long userId) throws Exception {
        Garden garden = gardenRepository.findById(gardenId).orElseThrow();
        Long DeviceId = garden.getDevice().getDeviceId();
        FunctionMessage fm = new FunctionMessage(1,"물 주기",userId);
        mqttOutboundGateway.publish("device/"+ DeviceId,fm);
    }

    @Override
    public void takePicture(Long gardenId, Long userId) throws Exception {

        Garden garden = gardenRepository.findById(gardenId).orElseThrow();
        Long DeviceId = garden.getDevice().getDeviceId();
        FunctionMessage fm = new FunctionMessage(2,"사진 찍기",userId);
        mqttOutboundGateway.publish("device/"+ DeviceId,fm);
    }

    @Override
    public GetGardenDataResponse getGardenStatus(Long gardenId) throws Exception {
        List<GardenStatus> list =gardenRepository.findGardenStatusFromLastWeek(gardenId, LocalDate.now().minusDays(7));
        List<GetGardenDataResponse> responses = new ArrayList<>();
        for(GardenStatus gardenStatus : list){
            GetGardenDataResponse response = new GetGardenDataResponse();
            response.setIlluminance(gardenStatus.getIlluminance());
            response.setSoil_moisture(gardenStatus.getSoil_moisture());
            response.setTemperature(gardenStatus.getTemperature());
            response.setCreatedDate(gardenStatus.getCreatedDate());
            response.setHumidity(gardenStatus.getHumidity());
            responses.add(response);
        }
        responses.sort(new Comparator<GetGardenDataResponse>() {
            @Override
            public int compare(GetGardenDataResponse o1, GetGardenDataResponse o2) {
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            }
        });
        return responses.get(0);
    }

    @Override
    public void changeGardenName(Long gardenId, String newName) throws Exception {
        Garden garden = gardenRepository.findById(gardenId).orElseThrow();
        garden.changeName(new String(newName.getBytes()));
    }


}
