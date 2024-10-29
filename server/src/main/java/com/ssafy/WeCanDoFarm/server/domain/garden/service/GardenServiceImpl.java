package com.ssafy.WeCanDoFarm.server.domain.garden.service;

import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.dto.RegisterUserToGardenRequest;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.UserToGarden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.UserToGardenRepository;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Garden garden = Garden.create(request.getGardenName(),"", request.getGardenAddress(), request.getDeviceSN());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new BaseException(ErrorCode.NOT_FOUND_USER));
        UserToGarden userToGarden = UserToGarden.create(user,garden);
        gardenRepository.save(garden);
        userToGardenRepository.save(userToGarden);
    }

    @Override
    public void registerUserToGarden(RegisterUserToGardenRequest request) throws Exception {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new BaseException(ErrorCode.NOT_FOUND_USER));
        UserToGarden userToGarden = UserToGarden.create(user,garden);
        userToGardenRepository.save(userToGarden);

    }
}
