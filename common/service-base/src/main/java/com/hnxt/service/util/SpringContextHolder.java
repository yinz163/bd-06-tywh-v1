package com.hnxt.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author yinz
 * @Date 2021/9/15 - 10:21
 */
@Component
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("初始化SpringContextHolder......");
		if(applicationContext != null) {
			SpringContextHolder.applicationContext = applicationContext;
		}
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}
}
