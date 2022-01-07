package com.hnxt;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author yinz
 * @Date 2021/11/24 - 10:18
 */
@SpringBootApplication(scanBasePackages = {"com.hnxt"}, exclude = {SecurityAutoConfiguration.class})
public class ActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
}
