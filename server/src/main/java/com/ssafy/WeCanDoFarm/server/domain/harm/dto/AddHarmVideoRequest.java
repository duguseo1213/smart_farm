package com.ssafy.WeCanDoFarm.server.domain.harm.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class AddHarmVideoRequest {
    Long harmPictureId;
    MultipartFile file;
}
