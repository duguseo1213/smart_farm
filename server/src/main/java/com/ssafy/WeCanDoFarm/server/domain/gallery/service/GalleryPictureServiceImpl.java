package com.ssafy.WeCanDoFarm.server.domain.gallery.service;

import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.AddPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.GetPictureOnDateRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.GetPicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.UpdateDescriptionRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.entity.GalleryPicture;
import com.ssafy.WeCanDoFarm.server.domain.gallery.repository.GalleryPictureRepository;
import com.ssafy.WeCanDoFarm.server.domain.garden.entity.Garden;
import com.ssafy.WeCanDoFarm.server.domain.garden.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GalleryPictureServiceImpl implements GalleryPictureService{
    private final GalleryPictureRepository galleryPictureRepository;
    private final GardenRepository gardenRepository;

    @Override
    public void addPicture(AddPictureRequest request) throws Exception {
        Garden garden = gardenRepository.findById(request.getGardenId()).orElseThrow(() -> new BaseException(ErrorCode.SERVER_ERROR));
        GalleryPicture galleryPicture = GalleryPicture.create(garden,request.getImage(), request.getDescription());
        galleryPictureRepository.save(galleryPicture);
    }

    @Override
    public void updateDescription(UpdateDescriptionRequest request) throws Exception {
        GalleryPicture galleryPicture = galleryPictureRepository.findById(request.getPictureId()).orElseThrow(() -> new BaseException(ErrorCode.SERVER_ERROR));
        galleryPicture.setGalleryImageDescription(request.getDescription());
        galleryPictureRepository.save(galleryPicture);

    }

    @Override
    public List<GetPicturesResponse> getPictures(Long gardenId) throws Exception {
        List<GetPicturesResponse> pictures = new ArrayList<>();
        List<GalleryPicture> gallery = galleryPictureRepository.findByGardenId(gardenId);
        for(GalleryPicture galleryPicture : gallery){
            GetPicturesResponse getPicturesResponse = new GetPicturesResponse();
            getPicturesResponse.setPictureId(galleryPicture.getGalleryPictureId());
            getPicturesResponse.setPictureUrl(galleryPicture.getGalleryImage());
            getPicturesResponse.setPictureDescription(galleryPicture.getGalleryImageDescription());
            getPicturesResponse.setPictureDate(galleryPicture.getCreatedDate());
            pictures.add(getPicturesResponse);
        }
        return pictures;
    }

    @Override
    public List<GetPicturesResponse> getPicturesOnDate(GetPictureOnDateRequest request) throws Exception {
        List<GetPicturesResponse> pictures = new ArrayList<>();
        List<GalleryPicture> gallery = galleryPictureRepository.findByGardenIdOnDate(request.getGardenId(),request.getCreatedDate());
        for(GalleryPicture galleryPicture : gallery){
            GetPicturesResponse getPicturesResponse = new GetPicturesResponse();
            getPicturesResponse.setPictureId(galleryPicture.getGalleryPictureId());
            getPicturesResponse.setPictureUrl(galleryPicture.getGalleryImage());
            getPicturesResponse.setPictureDescription(galleryPicture.getGalleryImageDescription());
            getPicturesResponse.setPictureDate(galleryPicture.getCreatedDate());
            pictures.add(getPicturesResponse);
        }
        return pictures;
    }
}
