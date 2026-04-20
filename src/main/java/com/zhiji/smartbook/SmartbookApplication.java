// 路径：com.zhiji.smartbook.SmartbookApplication.java
package com.zhiji.smartbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhiji.smartbook.module.*.mapper") // ✅ 必须是这个路径！
public class SmartbookApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartbookApplication.class, args);
    }
}