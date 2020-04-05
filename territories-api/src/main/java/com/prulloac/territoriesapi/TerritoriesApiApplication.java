package com.prulloac.territoriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages =
		{
				"com.prulloac.territoriesapi",
				"com.prulloac.territoriesdto",
				"com.prulloac.territoriesdata"
		})
@EnableJpaRepositories(basePackages = {"com.prulloac.territoriesdata.db"})
@EntityScan(basePackages = {"com.prulloac.territoriesdata.model"})
@EnableCaching
@EnableAspectJAutoProxy
public class TerritoriesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerritoriesApiApplication.class, args);
	}

}
