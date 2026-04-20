package com.zhiji.smartbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 扫描所有包，保证Controller生效
@SpringBootApplication(scanBasePackages = "com.zhiji.smartbook")
@MapperScan("com.zhiji.smartbook.mapper")
public class SmartbookApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartbookApplication.class, args);
    }
}