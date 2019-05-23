package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.yy.maoyi.*","com.yy.*"})
@SpringBootApplication(scanBasePackages = {"com.yy.*"})
public class BootStrap  {
	public static void main(String[] args) {
		SpringApplication.run(BootStrap.class, args);
	}
}
