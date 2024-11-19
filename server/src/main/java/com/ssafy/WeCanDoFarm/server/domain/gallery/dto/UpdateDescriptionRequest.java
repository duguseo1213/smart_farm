package com.ssafy.WeCanDoFarm.server.domain.gallery.dto;

import lombok.Data;

@Data
public class UpdateDescriptionRequest {
    Long pictureId;
    String description;
}
