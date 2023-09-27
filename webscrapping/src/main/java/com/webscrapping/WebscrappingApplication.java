package com.webscrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.webscrapping.scrap.Scrap;

@SpringBootApplication
public class WebscrappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscrappingApplication.class, args);
		new Scrap().scrap();
	}
	
	@Bean
	public RestTemplate restTemplateBean(RestTemplateBuilder builder) {
		return builder.build();
	}

}
