package VaLocaProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        // Create a new RedisTemplate instance
        // RedisTemplate is the main class used to interact with Redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory); // Set the Redis connection factory

        // JDK binary serializer for values
        // Converts Java objects to bytes and back using Java's native serialization (not the Jackson serializer)
        // Works for any Serializable object
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();

        // Set the serializer for Redis keys as plain strings
        template.setKeySerializer(new StringRedisSerializer());

        // Set the serializer for Redis values using JDK serialization
        template.setValueSerializer(serializer);

        // Set the serializers for Redis hash keys and hash values
        // Hashes in Redis are like maps, this ensures keys are strings and values are serialized
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        // Finalize the template configuration
        template.afterPropertiesSet();

        // Return the fully configured RedisTemplate to Spring
        return template;
    }
}
