package com.manasys.manasys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ManasysApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ManasysApplication.class, args);
    }

}
