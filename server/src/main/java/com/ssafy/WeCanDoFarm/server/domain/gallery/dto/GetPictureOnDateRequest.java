package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class GetPictureOnDateRequest {
    Long gardenId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createdDate;
}
