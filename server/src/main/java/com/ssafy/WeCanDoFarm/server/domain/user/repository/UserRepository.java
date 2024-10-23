package com.najackdo.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.najackdo.server.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {
	/**
	 * 사용자 이름으로 사용자 조회
	 *
	 * @param username 사용자 이름
	 * @return {@link User} 사용자 엔티티 (존재하지 않으면, {@link Optional#empty()} 반환)
	 */
	@Query("""
		SELECT u 
		FROM User u
		LEFT JOIN FETCH  u.activityAreaSetting
		LEFT JOIN FETCH  u.activityAreaSetting.location
		WHERE u.username = :username
""")
	Optional<User> findByUsername(@Param("username") String username);

	/**
	 * 사용자 닉네임으로 사용자 조회
	 * @param nickname
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u.nickName = :nickname")
	Optional<User> findByNickname(@Param("nickname") String nickname);

}