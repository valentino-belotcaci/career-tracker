package VaLocaProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        // Create a new RedisTemplate instance
        // RedisTemplate is the main class used to interact with Redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory); // Set the Redis connection factory

        // Configure Jackson ObjectMapper to handle Java 8 date/time types
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // support LocalDate, LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // use ISO-8601 format

        // JSON serializer for values using Jackson2 and the configured ObjectMapper
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);

        // Set the serializer for Redis keys as plain strings
        template.setKeySerializer(new StringRedisSerializer());

        // Set the serializer for Redis values using JSON
        template.setValueSerializer(serializer);

        // Set the serializers for Redis hash keys and hash values
        // Hashes in Redis are like maps, this ensures keys are strings and values are serialized
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        // Finalize the template configuration
        template.afterPropertiesSet();

        // Return the configured RedisTemplate to Spring
        return template;
    }

    // This bean tells Spring how to automatically cache 
    // (in our case with Redis, so we need a RedisCacheManager)
    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        // JSON serializer for caching using the same ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // support LocalDate, LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // use ISO-8601 format

        mapper.activateDefaultTyping(
        mapper.getPolymorphicTypeValidator(),
        ObjectMapper.DefaultTyping.NON_FINAL
        );
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);

        // Configure RedisCacheManager to use the JSON serializer for cache values
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(serializer)
            );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
