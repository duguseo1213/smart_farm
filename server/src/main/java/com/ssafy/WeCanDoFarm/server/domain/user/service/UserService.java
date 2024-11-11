package com.ssafy.WeCanDoFarm.server.domain.user.service;


import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public void setFcmToken(User user, String fcmToken) {
        log.info("UserName {}",user.getUsername());
        log.info("Setting FCM token to {}", fcmToken);
        user.pushToken(fcmToken);
        userRepository.save(user);
        log.info("2 Setting FCM token to {}", user.getFcmToken());
    }
}
