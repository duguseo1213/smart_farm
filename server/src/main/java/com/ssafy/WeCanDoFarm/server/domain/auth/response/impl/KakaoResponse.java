package com.ssafy.WeCanDoFarm.server.domain.auth.response.impl;

import com.ssafy.WeCanDoFarm.server.domain.auth.response.OAuth2Response;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.ProviderType;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {
	private final Map<String, Object> attribute;
	private final Map<String, Object> properties;
	private final Map<String, Object> kakaoAccount;
	private final String profile_image;

	public KakaoResponse(Map<String, Object> attributes) {
		this.attribute = attributes;
		this.properties = (Map<String, Object>)attributes.get("properties");
		this.kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		this.profile_image = properties.get("profile_image").toString();
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.KAKAO;
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getName() {
		return properties.get("nickname").toString();
	}

	@Override
	public String getGender() {
		return "M";
	}

	@Override
	public String getProfileImage() {
		return profile_image;
	}
}