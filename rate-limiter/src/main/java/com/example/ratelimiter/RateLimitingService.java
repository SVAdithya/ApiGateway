package com.example.ratelimiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimitingService {

    private final StringRedisTemplate redisTemplate;
    private final int allowedRequests;
    private final int windowSeconds;

    public RateLimitingService(StringRedisTemplate redisTemplate,
                               @Value("${ratelimit.requests}") int allowedRequests,
                               @Value("${ratelimit.seconds}") int windowSeconds) {
        this.redisTemplate = redisTemplate;
        this.allowedRequests = allowedRequests;
        this.windowSeconds = windowSeconds;
    }

    public boolean isAllowed(String ipAddress) {
        String key = "rate-limit:" + ipAddress;
        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount == null) {
            // Should not happen with increment, but as a safeguard
            redisTemplate.opsForValue().set(key, "1", windowSeconds, TimeUnit.SECONDS);
            return true;
        }

        if (currentCount == 1L) {
            // First request for this IP in the current window, set expiry
            redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
        }

        return currentCount <= allowedRequests;
    }
}
