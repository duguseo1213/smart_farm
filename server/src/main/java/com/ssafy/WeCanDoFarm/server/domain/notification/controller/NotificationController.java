package com.ssafy.WeCanDoFarm.server.domain.notification.controller;

import com.ssafy.WeCanDoFarm.server.core.annotation.CurrentUser;
import com.ssafy.WeCanDoFarm.server.domain.notification.event.NotificationEvent;
import com.ssafy.WeCanDoFarm.server.domain.notification.service.NotificationService;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/notice")
@RequiredArgsConstructor
@Tag(name = "알람 관련 API ")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/alarm-test")
    public void alarmTest(@CurrentUser User user) {
        Long targetUserId = user.getId();
        String title = "test1";
        String body = "test2";
        notificationService.sendNotificationEvent(new NotificationEvent(targetUserId, title, body));
    }

}
