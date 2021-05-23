package com.geek.hmilydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HmilyDemoAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoAccountApplication.class, args);
    }

}
