package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;

import java.util.Date;
@Data
public class GetPictureOnDateRequest {
    Long gardenId;
    Date createdDate;
}
