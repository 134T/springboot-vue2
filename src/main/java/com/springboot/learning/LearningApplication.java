package com.springboot.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author asus
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.springboot.learning.mapper")
public class LearningApplication {

    public static void main(String[] args) {


        SpringApplication.run(LearningApplication.class, args);


    }

}
