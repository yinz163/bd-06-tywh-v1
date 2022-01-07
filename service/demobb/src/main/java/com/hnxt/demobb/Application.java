package com.hnxt.demobb;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.hnxt.common.vo.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yinz
 * @Date 2021/10/25 - 14:51
 */
@SpringBootApplication(scanBasePackages = {"com.hnxt"}, exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		DruidDataSourceAutoConfigure.class ,
		HibernateJpaAutoConfiguration.class})
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/test")
	public R test() {
		return R.ok();
	}

	@GetMapping("/test1")
	public R test1() {
		int i = 10 / 0;
		return R.ok();
	}

	@GetMapping("/test3")
//	@PreAuthorize("hasAuthority('user.list')")
	public R test3() {
		return R.ok();
	}

	@GetMapping("/test4")
//	@PreAuthorize("hasAuthority('user.list11')")
	public R test4() {
		return R.ok();
	}
}
