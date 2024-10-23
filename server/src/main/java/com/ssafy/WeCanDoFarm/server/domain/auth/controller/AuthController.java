package com.najackdo.server.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.najackdo.server.core.annotation.Authenticated;
import com.najackdo.server.core.annotation.CurrentUser;
import com.najackdo.server.core.constants.AuthConst;
import com.najackdo.server.core.exception.BaseException;
import com.najackdo.server.core.exception.ErrorCode;
import com.najackdo.server.core.properties.JwtProperties;
import com.najackdo.server.core.response.SuccessResponse;
import com.najackdo.server.core.util.CookieUtils;
import com.najackdo.server.domain.auth.entity.JwtToken;
import com.najackdo.server.domain.auth.service.AuthService;
import com.najackdo.server.domain.user.entity.User;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Tag(name = "인증 인가 관련 API")
public class AuthController {
	private final JwtProperties properties;
	private final AuthService authService;

	@Authenticated
	@PostMapping(value = "/sign-out")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public SuccessResponse<Void> signOut(
		@CurrentUser User user,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
			() -> new BaseException(ErrorCode.UNAUTHORIZED)
		);

		authService.signOut(user, cookie.getValue());
		CookieUtils.removeCookie(response, AuthConst.REFRESH_TOKEN);
		return SuccessResponse.empty();
	}

	@PostMapping(value = "/refresh")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<JwtToken> refresh(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
			() -> new BaseException(ErrorCode.UNAUTHORIZED)
		);

		JwtToken refreshedToken = authService.refresh(cookie.getValue());

		CookieUtils.removeCookie(response, AuthConst.REFRESH_TOKEN);
		CookieUtils.addCookie(response, AuthConst.REFRESH_TOKEN, refreshedToken.getRefreshToken(), properties.getRefreshExpire(), true);
		return SuccessResponse.of(refreshedToken);
	}

	@Authenticated
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse<Void> deleteUser(@CurrentUser User user) {
		authService.deleteUser(user);
		return SuccessResponse.empty();
	}



}