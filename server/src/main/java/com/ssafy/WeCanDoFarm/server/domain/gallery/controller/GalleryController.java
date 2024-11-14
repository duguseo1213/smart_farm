package com.ssafy.WeCanDoFarm.server.domain.gallery.controller;

import com.ssafy.WeCanDoFarm.server.core.response.SuccessResponse;
import com.ssafy.WeCanDoFarm.server.domain.S3.service.S3UploadService;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.AddPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.GetPicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.TransferPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.UpdateDescriptionRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.service.GalleryPictureService;
import com.ssafy.WeCanDoFarm.server.domain.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final S3UploadService s3UploadService;
    private final ApplicationEventPublisher eventPublisher;
    private final GalleryPictureService galleryPictureService;

    @PostMapping("/transfer-picture")
    public SuccessResponse<String> transferPicture(@RequestBody TransferPictureRequest transferPictureRequest) throws Exception{
        String image = s3UploadService.upload(transferPictureRequest.getImage());
        eventPublisher.publishEvent(new NotificationEvent(transferPictureRequest.getUserId(),"",image));
        return SuccessResponse.of("Taking picture");
    }

    @PostMapping("/add-picture")
    public SuccessResponse<String> addPicture(@RequestBody AddPictureRequest request) throws Exception
    {
        galleryPictureService.addPicture(request);
        return SuccessResponse.of("Add picture");
    }

    @PatchMapping("/update-description")
    public SuccessResponse<String> updateDescription(@RequestBody UpdateDescriptionRequest request) throws Exception
    {
        galleryPictureService.updateDescription(request);
        return SuccessResponse.of("update picture");
    }

    @GetMapping("/get-pictures")
    public SuccessResponse<List<GetPicturesResponse>> getPictures(@RequestParam Long gardenId) throws Exception
    {

        return SuccessResponse.of(galleryPictureService.getPictures(gardenId));
    }




}
