package VaLocaProject.Security;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

// IMPORTANT:
// NOW OBSOLETE AS WE USE SPRING CACHE ABSTRACTION WITH REDIS
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW = Duration.ofMinutes(1);


    // Save value with optional TTL
    public void save(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    // Get value
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete value
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // Increment counter (for rate limiting)
    public void checkRateLimit(String key) {
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, WINDOW); // set TTL 
        }

        if (count != null && count > MAX_REQUESTS) {
            throw new RuntimeException("Too many requests");
        }
    }

}
