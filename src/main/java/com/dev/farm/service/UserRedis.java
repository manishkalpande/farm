package com.dev.farm.service;


import com.dev.farm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRedis {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY = "USER";

    public void save(User user) {
        redisTemplate.opsForHash().put(KEY, user.getId(), user);
    }

    public User findById(String id) {
        return (User) redisTemplate.opsForHash().get(KEY, id);
    }

    public void delete(String id) {
        redisTemplate.opsForHash().delete(KEY, id);
    }
}
