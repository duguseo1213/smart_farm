package com.ssafy.WeCanDoFarm.server.domain.meteorogic.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AreaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="area_id")
    long areaId;

    @Column(name = "area_code")
    String areaCode;

    @Column(name="area_name")
    String areaName;

}
