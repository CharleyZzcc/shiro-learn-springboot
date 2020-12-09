package com.lzc.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class ShiroWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroWebApplication.class, args);
	}

}
