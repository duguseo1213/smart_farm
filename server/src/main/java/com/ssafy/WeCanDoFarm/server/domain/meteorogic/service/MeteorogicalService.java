package com.ssafy.WeCanDoFarm.server.domain.meteorogic.service;


import com.ssafy.WeCanDoFarm.server.core.exception.BaseException;
import com.ssafy.WeCanDoFarm.server.core.exception.ErrorCode;
import com.ssafy.WeCanDoFarm.server.domain.meteorogic.entity.AreaCode;
import com.ssafy.WeCanDoFarm.server.domain.meteorogic.repository.MeteorogicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MeteorogicalService {

    @Value("${kakao-rest-api-key}")
    private String apiKey;

    private final MeteorogicRepository meteorogicRepository;

    public String conversionToAddress(String latitude,String longitude){
        return AddressService.coordToAddr(longitude,latitude,apiKey);
    }

    public String getAreaCode(String address){
        List<AreaCode> list = meteorogicRepository.findAll();

        for( AreaCode areaCode : list){
            if(address.contains(areaCode.getAreaName())){
                return areaCode.getAreaCode();
            }
        }
        return "none";
    }


}
