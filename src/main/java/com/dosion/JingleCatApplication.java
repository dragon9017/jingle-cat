package com.dosion;

import com.dosion.annotation.swagger.EnableDCloudXSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * springBoot 启动入口
 * @author 陈登文
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDCloudXSwagger2
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class JingleCatApplication {
    public static void main(String[] args) {
        SpringApplication.run(JingleCatApplication.class, args);
    }
}
