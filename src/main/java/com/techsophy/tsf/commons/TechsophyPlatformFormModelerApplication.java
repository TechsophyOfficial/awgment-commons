package com.techsophy.tsf.commons;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import static com.techsophy.tsf.commons.constants.CommonConstants.MULTI_TENANCY_PACKAGE_NAME;
import static com.techsophy.tsf.commons.constants.CommonConstants.PACKAGE_NAME;


@RefreshScope
@EnableMongoRepositories
@EnableMongoAuditing
@ComponentScan({PACKAGE_NAME, MULTI_TENANCY_PACKAGE_NAME})
@SpringBootApplication
@OpenAPIDefinition

public class TechsophyPlatformFormModelerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TechsophyPlatformFormModelerApplication.class, args);
	}
}
