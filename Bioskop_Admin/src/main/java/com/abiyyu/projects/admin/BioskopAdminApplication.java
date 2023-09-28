package com.abiyyu.projects.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.abiyyu.projects.libary.*","com.abiyyu.projects.admin.*"})
@EnableJpaRepositories(value = "com.abiyyu.projects.libary.repositories")
@EntityScan(value = "com.abiyyu.projects.libary.entity")
public class BioskopAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(BioskopAdminApplication.class, args);
	}
}
