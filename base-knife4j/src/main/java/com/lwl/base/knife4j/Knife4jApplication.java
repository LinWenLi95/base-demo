package com.lwl.base.knife4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Admin
 */
@EnableScheduling
@SpringBootApplication
public class Knife4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Knife4jApplication.class, args);
    }
}
