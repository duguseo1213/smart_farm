package com.ssafy.WeCanDoFarm.server.domain.user.repository;

import com.ssafy.WeCanDoFarm.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * 사용자 이름으로 사용자 조회
	 *
	 * @param username 사용자 이름
	 * @return {@link User} 사용자 엔티티 (존재하지 않으면, {@link Optional#empty()} 반환)
	 */
	@Query("""
		SELECT u 
		FROM User u
		WHERE u.username = :username
""")
	Optional<User> findByUsername(@Param("username") String username);
}