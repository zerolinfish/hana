package com.hana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HanaItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanaItemApplication.class,args);
    }
}
