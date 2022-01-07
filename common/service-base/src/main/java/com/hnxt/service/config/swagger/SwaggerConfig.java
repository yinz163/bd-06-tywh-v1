package com.hnxt.service.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author yinz
 * @Date 2021/8/30 - 16:56
 */
@Configuration//配置类
@EnableSwagger2 //swagger注解
public class SwaggerConfig {
	@Bean
	public Docket webApiConfig(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("webApi")
				.apiInfo(webApiInfo())
				.select()
				//.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build();

	}

	private ApiInfo webApiInfo(){

		return new ApiInfoBuilder()
				.title("同源维护")
				.description("本文档描述了同源维护微服务接口定义")
				.version("1.0")
				.contact(new Contact("java", "http://atguigu.com", "591688286@qq.com"))
				.build();
	}
}
