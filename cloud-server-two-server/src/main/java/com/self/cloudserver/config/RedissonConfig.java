package com.self.cloudserver.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:10.12.2.64}")
    private String redisIp;

    @Value("${spring.redis.port:6379}")
    private String redisPort;

    @Value("${spring.redis.password:sy_202106}")
    private String redisPwd;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();

        singleServerConfig.setAddress("redis://" + redisIp + ":" + redisPort);
        singleServerConfig.setPassword(redisPwd);

        return Redisson.create(config);
    }

}
