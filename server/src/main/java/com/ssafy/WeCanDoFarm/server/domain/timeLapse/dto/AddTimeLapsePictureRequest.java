package com.ssafy.WeCanDoFarm.server.domain.timeLapse.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddTimeLapsePictureRequest {
    Long gardenId;
    MultipartFile image;
}
