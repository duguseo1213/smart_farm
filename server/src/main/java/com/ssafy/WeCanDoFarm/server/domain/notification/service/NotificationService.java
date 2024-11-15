package com.ssafy.WeCanDoFarm.server.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.notification.event.NotificationEvent;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import com.ssafy.WeCanDoFarm.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    private final UserRepository usersRepository;


    @EventListener
    // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendNotificationEvent(NotificationEvent notificationEvent) {
        log.info("알람 전송!");
        User user = usersRepository.findById(notificationEvent.getTargetUserId())
                .orElseThrow(
                        () -> new BaseException(ErrorCode.NOT_FOUND_USER)
                );

        Notification notification = Notification.builder()
                .setTitle(notificationEvent.getTitle())
                .setBody(notificationEvent.getBody())
                .setImage(notificationEvent.getImageURL())
                .build();
        Message message = Message.builder()
                .setToken(user.getFcmToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new BaseException(ErrorCode.NOT_SENDED_ALARM);
        }
        log.info("전송! 완료");
    }
}
