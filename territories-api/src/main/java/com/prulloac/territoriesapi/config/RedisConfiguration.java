package com.prulloac.territoriesapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

	private RedisServer redisServer;

	// borrar esto al usar con redis real
	public RedisConfiguration(RedisProperties redisProperties) {
		redisServer = new RedisServer(redisProperties.getRedisPort());
	}

	@PostConstruct
	public void postConstruct() {
		redisServer.start();
	}

	@PreDestroy
	public void preDestroy() {
		redisServer.stop();
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory(
			RedisProperties redisProperties) {
		return new LettuceConnectionFactory(
				redisProperties.getRedisHost(),
				redisProperties.getRedisPort());
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
