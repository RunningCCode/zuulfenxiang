package com.yunhua.czc.zuulfenxiang.gatewayconfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenzhicong
 * @time 2019/11/24 0:11
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.yunhuakeji","com.yunhua"})
@MapperScan(value = {"com.yunhua.czc.zuulfenxiang.gatewayconfig.dao"})
@EnableFeignClients(basePackages = "com.yunhua")
public class GatewayConfigApplication {
    public static void main(String[] args){
        SpringApplication.run(GatewayConfigApplication.class, args);
    }
}

