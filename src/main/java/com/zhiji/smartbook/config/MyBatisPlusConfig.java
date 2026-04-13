package com.zhiji.smartbook.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zhiji.smartbook.module.*.mapper")
public class MyBatisPlusConfig {
}