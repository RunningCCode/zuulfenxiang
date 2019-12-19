package com.yunhua.czc.zuulfenxiang.gatewaymanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author chenzhicong
 * @time 2019/11/24 0:11
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.yunhuakeji","com.yunhua"})
@EnableFeignClients(basePackages = "com.yunhua")
public class GatewayManagerApplication {
    public static void main(String[] args){
        SpringApplication.run(GatewayManagerApplication.class, args);
    }
}

