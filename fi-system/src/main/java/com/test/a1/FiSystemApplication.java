package com.test.a1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {FiSystemApplication.class})
public class FiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiSystemApplication.class, args);
    }

}

