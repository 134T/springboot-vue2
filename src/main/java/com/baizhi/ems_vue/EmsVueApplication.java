package com.baizhi.ems_vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author asus
 */
@SpringBootApplication
@MapperScan(basePackages = "com.baizhi.ems_vue.mapper")
@EnableTransactionManagement
public class EmsVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsVueApplication.class, args);
    }

}
