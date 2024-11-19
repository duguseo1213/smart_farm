package com.ssafy.WeCanDoFarm.server.domain.auth.response.impl;

import com.ssafy.WeCanDoFarm.server.domain.auth.response.OAuth2Response;
import com.ssafy.WeCanDoFarm.server.domain.user.entity.ProviderType;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
	private final Map<String, Object> attribute;

	public NaverResponse(Map<String, Object> attribute) {
		this.attribute = (Map<String, Object>)attribute.get("response");
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.NAVER;
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		return attribute.get("email").toString();
	}

	@Override
	public String getName() {
		return attribute.get("name").toString();
	}

	@Override
	public String getGender() {
		return attribute.get("gender").toString();
	}

	@Override
	public String getProfileImage() {
		return attribute.get("profile_image").toString();
	}
}
