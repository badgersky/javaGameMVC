package com.example.javagamemvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.example.javagamemvc.entity")
@EnableJpaRepositories("com.example.javagamemvc.repository")
@SpringBootApplication
public class JavaGameMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaGameMvcApplication.class, args);
    }

}
