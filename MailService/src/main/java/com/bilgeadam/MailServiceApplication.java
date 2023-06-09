package com.bilgeadam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MailServiceApplication {
    public static void main(String[] args) {
        System.out.println("buraya geldi");
        SpringApplication.run(MailServiceApplication.class);
    }
}
