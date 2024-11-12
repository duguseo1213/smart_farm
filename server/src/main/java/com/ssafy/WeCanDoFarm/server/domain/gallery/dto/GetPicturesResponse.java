package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetPicturesResponse {
    Long pictureId;
    String pictureUrl;
    String pictureDescription;
    Date pictureDate;
}
