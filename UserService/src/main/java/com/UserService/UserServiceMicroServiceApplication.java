package com.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

public class UserServiceMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceMicroServiceApplication.class, args);
    }

    @Bean
    
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
