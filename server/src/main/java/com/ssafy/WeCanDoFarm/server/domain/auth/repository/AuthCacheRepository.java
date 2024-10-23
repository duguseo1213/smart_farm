package com.najackdo.server.domain.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.najackdo.server.core.properties.JwtProperties;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties properties;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, properties.getRefreshExpire());
    }

    public String findByUsername(String username) {
        return (String) redisTemplate.opsForValue().get(username);
    }

    public boolean existsByUsername(String username) {
        return redisTemplate.opsForValue().get(username) != null;
    }
}
