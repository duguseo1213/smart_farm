package com.najackdo.server.domain.auth.handler;

import static com.najackdo.server.core.constants.AuthConst.*;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.najackdo.server.core.constants.AuthConst;
import com.najackdo.server.core.properties.JwtProperties;
import com.najackdo.server.core.util.CookieUtils;
import com.najackdo.server.domain.auth.entity.JwtToken;
import com.najackdo.server.domain.auth.repository.CustomAuthorizationRequestRepository;
import com.najackdo.server.domain.auth.response.CustomOAuth2User;
import com.najackdo.server.domain.auth.service.JWTService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JWTService jwtService;
	private final JwtProperties properties;
	private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;


	@Override
	protected String determineTargetUrl(
		HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
		clearAuthenticationAttributes(request, response);
		return redirectUri.orElse(getDefaultTargetUrl());
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException {
		CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

		JwtToken jwtToken = jwtService.generateToken(customUserDetails.getUsername(), customUserDetails.getAuthorities());
		CookieUtils.addCookie(response, AuthConst.REFRESH_TOKEN, jwtToken.getRefreshToken(), properties.getRefreshExpire(), true);

		String redirectURI = determineTargetUrl(request, response, authentication);
		getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(redirectURI, jwtToken));
	}

	private String getRedirectUrl(String targetUrl, JwtToken token) {
		return UriComponentsBuilder.fromUriString(targetUrl + "/auth/callback")
			.queryParam("accessToken", token.getAccessToken())
			.build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}
