package com.thenextone.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigServer
@SpringBootApplication(scanBasePackages = {"com.thenextone.app","com.thenextone.config","com.thenextone.core"})
@EnableJpaRepositories(basePackages = {"com.thenextone.config","com.thenextone.core"})
@EntityScan(basePackages = {"com.thenextone.config","com.thenextone.core"})
public class TheNextOneMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheNextOneMasterApplication.class, args);
	}

}
