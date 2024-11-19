package com.ssafy.WeCanDoFarm.server.domain.garden.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Data
public class GetGardenDataResponse {
    private Double temperature;
    private Double humidity;
    private Double illuminance;
    private Double soil_moisture;
    private Date createdDate;
}
