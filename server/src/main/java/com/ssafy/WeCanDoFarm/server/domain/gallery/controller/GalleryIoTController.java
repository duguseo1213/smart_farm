package com.ssafy.WeCanDoFarm.server.domain.gallery.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.S3.service.S3UploadService;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.TransferPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.service.GalleryPictureService;
import com.ssafy.WeCanDoFarm.server.domain.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v2/gallery")
@RequiredArgsConstructor
public class GalleryIoTController {

    private final S3UploadService s3UploadService;
    private final ApplicationEventPublisher eventPublisher;
    private final GalleryPictureService galleryPictureService;

    @PostMapping("/transfer-picture")
    public SuccessResponse<String> transferPicture(TransferPictureRequest transferPictureRequest) throws Exception{
        String image = s3UploadService.upload(transferPictureRequest.getImage());
        eventPublisher.publishEvent(new NotificationEvent(transferPictureRequest.getUserId(),"사진촬영 알람입니다!!","이 사진을 저장하시겠습니까?",image));
        return SuccessResponse.of("Taking picture");
    }
}
