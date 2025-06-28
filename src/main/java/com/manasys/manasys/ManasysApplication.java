package com.manasys.manasys;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        SpringApplication.run(ManasysApplication.class, args);
    }

}
