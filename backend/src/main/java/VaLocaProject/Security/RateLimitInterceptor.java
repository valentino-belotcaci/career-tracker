package VaLocaProject.Security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisService redisService;

    public RateLimitInterceptor(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Use IP address + the URL path as the unique key
        String key = "rate_limit:" + request.getRemoteAddr() + ":" + request.getRequestURI();
        
        try {
            redisService.checkRateLimit(key);
            return true; // Allow request
        } catch (RuntimeException e) {
            throw new RuntimeException("You are making too many requests!!!!!!!!!!!!!");
        }
    }
}