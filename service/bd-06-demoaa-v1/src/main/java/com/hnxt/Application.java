package com.hnxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yinz
 * @Date 2021/8/30 - 18:18
 */
@SpringBootApplication(scanBasePackages = {"com.hnxt"})
@RestController
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.hnxt.demoaa.mapper"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
