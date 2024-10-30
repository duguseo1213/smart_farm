package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.GetUserFromGardenResponse;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterUserToGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.GardenUserType;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.UserToGardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GardenServiceImpl implements GardenService {

    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final UserToGardenRepository userToGardenRepository;

    @Override
    public List<Garden> getGardens(String username) throws Exception {
        return gardenRepository.getGardens(username);
    }

    @Override
    public void registerGarden(RegisterGardenRequest request) throws Exception {
        Garden garden = Garden.create(request.getGardenName(), "", request.getGardenAddress(), request.getDeviceSN());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        UserToGarden userToGarden = UserToGarden.create(user, garden, GardenUserType.LEADER);
        gardenRepository.save(garden);
        userToGardenRepository.save(userToGarden);
    }

    @Override
    public void registerUserToGarden(RegisterUserToGardenRequest request) throws Exception {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
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
}
