package VaLocaProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

// A bean class, so its scaned by Spring at startup
// Every bean method becomes part of the context
@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Creates a serializer as redis only understands strings and byte arrays
        // So we set that by default JAVA objects are serialised in JSON(byte array)
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // keep String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // use a single default serializer for values 
        template.setDefaultSerializer(jsonSerializer);


        return template;
    }
}
