package com.ssafy.WeCanDoFarm.server.domain.harm.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetHarmHumanPictureResponse {
    Long harmPictureId;
    String image;
    Date createdDate;
}
