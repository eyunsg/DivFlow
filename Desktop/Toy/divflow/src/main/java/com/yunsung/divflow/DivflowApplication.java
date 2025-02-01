package com.yunsung.divflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DivflowApplication {

    public static void main(String[] args) {

        SpringApplication.run(DivflowApplication.class, args);

        System.out.println("http://localhost:8080/");
    }
}
