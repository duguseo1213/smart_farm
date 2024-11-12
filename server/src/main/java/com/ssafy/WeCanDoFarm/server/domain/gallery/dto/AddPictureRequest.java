package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;

@Data
public class AddPictureRequest {
    String image;
    String description;
    Long gardenId;
}
