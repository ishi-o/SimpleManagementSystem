package com.manasys.manasys;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ManasysApplication {

    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        ConfigurableApplicationContext context = SpringApplication.run(ManasysApplication.class, args);
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        //     UserService userServ = context.getBean(UserService.class);
        //     try {
        //         userServ.logOut();
        //     } catch (Exception e) {
        //     }
        // }));
    }

}
