package com.weiran.mission;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.weiran.mission.mapper")
@ComponentScan(basePackages = {"com.weiran"}) // 扫描其他包如common模块的Bean
public class MissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionApplication.class, args);
    }
}