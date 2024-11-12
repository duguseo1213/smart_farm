package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TransferPictureRequest {
    MultipartFile image;
    Long userId;
}
