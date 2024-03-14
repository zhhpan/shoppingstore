package org.spm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class StaticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaticsApplication.class,args);
    }
}
