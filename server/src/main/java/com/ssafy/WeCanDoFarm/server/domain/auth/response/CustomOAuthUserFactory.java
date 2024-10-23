package com.najackdo.server.domain.auth.response;

import java.util.Map;

import com.najackdo.server.domain.auth.response.impl.GoogleResponse;
import com.najackdo.server.domain.auth.response.impl.KakaoResponse;
import com.najackdo.server.domain.auth.response.impl.NaverResponse;
import com.najackdo.server.domain.user.entity.ProviderType;

public class CustomOAuthUserFactory {
    public static OAuth2Response parseOAuth2Response(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case NAVER -> new NaverResponse(attributes);
            case GOOGLE -> new GoogleResponse(attributes);
            case KAKAO -> new KakaoResponse(attributes);
        };
    }
}
