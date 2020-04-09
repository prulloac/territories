package com.prulloac.territoriesapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages =
		{
				"com.prulloac.territoriesbase",
				"com.prulloac.territoriesapi",
				"com.prulloac.territoriesdto",
				"com.prulloac.territoriesdata"
		},
		exclude = RepositoryRestMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackages = {"com.prulloac.territoriesdata.db"})
@EntityScan(basePackages = {"com.prulloac.territoriesdata.model"})
@EnableCaching
@EnableAspectJAutoProxy
@EnableSwagger2
public class TerritoriesApiApplication {

	@Value("${app.name:'no name'}")
	private String name;
	@Value("${app.version:'0.0.1'}")
	private String version;
	@Value("${app.description:'no description'}")
	private String description;

	public static void main(String[] args) {
		SpringApplication.run(TerritoriesApiApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(Predicates.and(RequestHandlerSelectors.basePackage("com.prulloac.territoriesapi.rest"),
						Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(name)
				.version(version)
				.description(description)
				.build();

	}

}
