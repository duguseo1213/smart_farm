package com.ssafy.WeCanDoFarm.server.domain.user.controller;


import com.ssafy.WeCanDoFarm.server.core.annotation.CurrentUser;
import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
import com.ssafy.WeCanDoFarm.server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
@Tag(name = "유저 정보 관련 API")
public class UserController {
    private final UserService userService;

    @PostMapping("/set-fcm-token")
    public SuccessResponse<Void> setFcmToken(@CurrentUser User user, String fcmToken) {
        userService.setFcmToken(user, fcmToken);
        return SuccessResponse.empty();
    }
}
