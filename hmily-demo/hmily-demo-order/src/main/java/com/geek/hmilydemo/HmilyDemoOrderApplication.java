package com.geek.hmilydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.geek.hmilydemo"})
public class HmilyDemoOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoOrderApplication.class, args);
    }

}
