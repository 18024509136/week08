package com.geek.hmilydemoeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HmilyDemoEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoEurekaApplication.class, args);
    }

}
