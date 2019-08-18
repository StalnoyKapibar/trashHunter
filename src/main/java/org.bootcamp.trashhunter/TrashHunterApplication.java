package org.bootcamp.trashhunter;

import org.bootcamp.trashhunter.config.InitData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrashHunterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrashHunterApplication.class, args);
    }

    @Bean(initMethod = "init")
    public InitData initData() {
        return new InitData();
    }

}
