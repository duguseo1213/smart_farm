package com.ssafy.WeCanDoFarm.server.domain.auth.response;

import com.ssafy.WeCanDoFarm.server.domain.auth.response.impl.GoogleResponse;
import com.ssafy.WeCanDoFarm.server.domain.auth.response.impl.KakaoResponse;
import com.ssafy.WeCanDoFarm.server.domain.auth.response.impl.NaverResponse;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.ProviderType;

import java.util.Map;

public class CustomOAuthUserFactory {
    public static OAuth2Response parseOAuth2Response(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case NAVER -> new NaverResponse(attributes);
            case GOOGLE -> new GoogleResponse(attributes);
            case KAKAO -> new KakaoResponse(attributes);
        };
    }
}
