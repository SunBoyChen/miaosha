package com.hua.miaosha.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisPoolFactory {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.timeout}")
    private int redisTimeout;

    @Value("${redis.poolMaxTotal}")
    private int redisPoolMaxTotal;

    @Value("${redis.poolMaxIdle}")
    private int redisPoolMaxIdle;

    @Value("${redis.poolMaxWait}")
    private int redisPoolMaxWait;


    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPoolMaxTotal);
        jedisPoolConfig.setMaxIdle(redisPoolMaxIdle);
        jedisPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, redisTimeout);
        return jedisPool;
    }

}
