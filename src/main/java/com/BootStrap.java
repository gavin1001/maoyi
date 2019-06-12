package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.yy.maoyi.*","com.yy.*"})
@MapperScan("com.yy.maoyi.dao")
@SpringBootApplication(scanBasePackages = {"com.yy.*"})
public class BootStrap  {
	public static void main(String[] args) {
		SpringApplication.run(BootStrap.class, args);
	}
}
