package com.najackdo.server.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najackdo.server.core.constants.AuthConst;
import com.najackdo.server.core.exception.BaseException;
import com.najackdo.server.core.exception.ErrorCode;
import com.najackdo.server.domain.auth.entity.JwtToken;
import com.najackdo.server.domain.auth.repository.AuthCacheRepository;
import com.najackdo.server.domain.user.entity.User;
import com.najackdo.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JWTService jwtService;
	private final AuthCacheRepository authCacheRepository;
	private final UserRepository userRepository;

	@Transactional
	public void signOut(User user, String refreshToken) {
		if (authCacheRepository.existsByUsername(getSignOutKey(user.getUsername()))) {
			throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
		}
		authCacheRepository.save(getSignOutKey(user.getUsername()), refreshToken);
	}

	public JwtToken refresh(String refreshToken) {
		try {
			String username = jwtService.getUsername(refreshToken);

			if (authCacheRepository.existsByUsername(getSignOutKey(username))) {
				throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
			}

			return jwtService.refreshToken(refreshToken);
		} catch (Exception e) {
			throw new BaseException(ErrorCode.EXPIRED_TOKEN, e);
		}
	}

	@Transactional
	public void deleteUser(User user) {
		user.delete();
	}

	private String getSignOutKey(String username) {
		return AuthConst.SIGN_OUT_CACHE_KEY + username;
	}
}
