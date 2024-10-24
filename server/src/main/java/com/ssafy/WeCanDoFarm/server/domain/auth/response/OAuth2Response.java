package com.ssafy.WeCanDoFarm.server.domain.auth.response;

import com.ssafy.WeCanDoFarm.server.domain.user.entity.ProviderType;

public interface OAuth2Response {
	ProviderType getProvider();

	String getProviderId();

	String getEmail();

	String getName();

	String getGender();

	String getProfileImage();
}
