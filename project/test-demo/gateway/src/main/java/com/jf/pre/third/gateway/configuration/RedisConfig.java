package com.jf.pre.third.gateway.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author liumg
 * @version 创建时间：2019/7/10.
 */
@Configuration
public class RedisConfig {

    /**
     * 配置自定义redisTemplate
     * @return RedisTemplate Bean
     */
    @Bean
    RedisTemplate<Serializable, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<Serializable, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Qualifier("lockScript")
    public RedisScript<Integer> acquireLockWithTimeout() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setScriptText("if redis.call(\"exists\", KEYS[1]) == 0 then \tlocal lock = redis.call(\"setex\", KEYS[1], unpack(ARGV)) return \"0\" else return \"1\" end ");
        redisScript.setResultType(Integer.class);
        return redisScript;
    }


    @Bean
    @Qualifier("unLockScript")
    public RedisScript<Integer> releaseLock() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setScriptText("if redis.call(\"exists\",KEYS[1]) == 1 then  local lockRelease = redis.call(\"del\",KEYS[1]) if lockRelease then return \"1\" end return \"0\" end return \"-1\"");
        redisScript.setResultType(Integer.class);
        return redisScript;
    }
}
