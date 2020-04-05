package com.prulloac.territoriesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class RedisProperties {

	@Getter
	private int redisPort;
	@Getter
	private String redisHost;

	public RedisProperties(
			@Value("${spring.redis.port}") int redisPort,
			@Value("${spring.redis.host}") String redisHost) {
		this.redisPort = redisPort;
		this.redisHost = redisHost;
	}

}