package com.ssafy.WeCanDoFarm.server.domain.gallery.service;


import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.AddPictureRequest;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.GetPicturesResponse;
import com.ssafy.WeCanDoFarm.server.domain.gallery.dto.UpdateDescriptionRequest;

import java.util.List;


public interface GalleryPictureService {
    void addPicture(AddPictureRequest request) throws Exception;
    void updateDescription(UpdateDescriptionRequest request) throws Exception;
    List<GetPicturesResponse> getPictures(Long gardenId) throws Exception;
}
