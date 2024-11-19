package com.ssafy.WeCanDoFarm.server.domain.meteorogic.dto;

import lombok.Data;

public class AreaCodeDto {
    @Data
    public static class AreaCodeResponse {
        private String longitude;
        private String latitude;
        private String areacode;

        public static AreaCodeResponse of(String longitude, String latitude, String areacode) {
            AreaCodeResponse response = new AreaCodeResponse();
            response.setLongitude(longitude);
            response.setLatitude(latitude);
            response.setAreacode(areacode);
            return response;
        }

        public static AreaCodeResponse empty() {
            AreaCodeResponse response = new AreaCodeResponse();
            response.setLongitude("");
            response.setLatitude("");
            response.setAreacode("");
            return response;
        }
    }

}
