package com.ilias.bored;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BoredApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoredApplication.class, args);
    }

}
