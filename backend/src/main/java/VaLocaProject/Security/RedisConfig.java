package VaLocaProject.Security;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    // Template needed to connect to Redis and perform operations
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Connect to actual redis server
        template.setConnectionFactory(connectionFactory);

        // Define a redis serializer that uses JSON format to trnasalte redis bytes
        RedisSerializer<Object> jsonSerializer = RedisSerializer.json();

        // Set keys and values type for normal and hash operations
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonSerializer);

        return template;
    }


    @Bean
    // Needed to configure spring cache abstraction with redis
    RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        RedisSerializer<Object> jsonSerializer = RedisSerializer.json();

        // Class used to configure redis cache behavior
        RedisCacheConfiguration config =
        // Based on default cache config of spring
            RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(name -> name + ":")
                // Define duration of cache entries
                .entryTtl(Duration.ofMinutes(30))
                // Tells redis to use json serializer for values
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
                );

        // Creates the actual cache manager
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
