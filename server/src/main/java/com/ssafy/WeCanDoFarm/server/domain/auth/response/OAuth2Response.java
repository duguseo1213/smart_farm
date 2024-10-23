package com.najackdo.server.domain.auth.response;

import com.najackdo.server.domain.user.entity.ProviderType;

public interface OAuth2Response {
	ProviderType getProvider();

	String getProviderId();

	String getEmail();

	String getName();

	String getGender();

	String getProfileImage();
}
