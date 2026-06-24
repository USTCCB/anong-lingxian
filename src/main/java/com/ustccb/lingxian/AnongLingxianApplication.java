package com.ustccb.lingxian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ustccb.lingxian.mapper")
public class AnongLingxianApplication {
    public static void main(String[] args) { SpringApplication.run(AnongLingxianApplication.class, args); }
}
