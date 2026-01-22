package VaLocaProject.Security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
    public Long increment(String key, Duration ttl) {
        Long count = redisTemplate.opsForValue().increment(key);
        // set expiration if first time
        if (count == 1) {
            redisTemplate.expire(key, ttl);
        }
        return count;
    }
}
