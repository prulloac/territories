package com.prulloac.territoriesapi;

import org.springframework.boot.test.context.SpringBootTest;

import com.prulloac.territoriesapi.config.TestRedisConfiguration;
import org.junit.jupiter.api.Test;

@SpringBootTest(classes = {TestRedisConfiguration.class})
class TerritoriesApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
