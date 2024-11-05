package com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetTimeLapsePicturesResponse {
    Long gardenId;
    String image;
    Date createdDate;
}
