package com.hnxt.security.config;

import com.hnxt.security.filter.TokenAuthenticationFilter;
import com.hnxt.security.filter.TokenLoginFilter;
import com.hnxt.security.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *  * <p>
 *  * Security配置类
 *  权限注解@PreAuthorize最终都是通过类SecurityExpressionRoot中的方法与登录用户的权限进行匹配（字符串完全匹配），例如：hasAuthority(String authority)
 *  * </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:34
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private TokenManager tokenManager;
	private DefaultPasswordEncoder defaultPasswordEncoder;
	private RedisTemplate redisTemplate;


	@Autowired
	public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
								  TokenManager tokenManager, RedisTemplate redisTemplate) {
		this.userDetailsService = userDetailsService;
		this.defaultPasswordEncoder = defaultPasswordEncoder;
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 配置设置
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class)  //添加过滤器实现验证码登录逻辑
		http.exceptionHandling()
				.authenticationEntryPoint(new UnauthenticatedEntryPoint())//未认证处理类
                .accessDeniedHandler(new UnAuthorizedEntryPoint())  //未授权处理类
				.and().csrf().disable()
				.authorizeRequests()
				.anyRequest().authenticated()
				.and().logout().logoutUrl("/admin/acl/index/logout")
				.addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
				.addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))//此处可不配置，可通过controller接口实现登录
				.addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
	}

	/**
	 * 密码处理
	 * @param auth
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
		//添加密码校验逻辑,可配置多种Provider(集成多种登录方式,循环校验，直到有一种验证成功为止，可查看ProviderManager.authenticate())，默认DaoAuthenticationProvider
		auth.authenticationProvider(new TokenAuthenticationProvider(userDetailsService, defaultPasswordEncoder))
				//配置DaoAuthenticationProvider
				.userDetailsService(userDetailsService);

	}

	/**
	 * 配置过滤器链（上述自定义、内置）哪些请求不拦截(不需要登录访问)
	 * 此处配置了swagger、登录接口不拦截（最后一个）
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/**",
				"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/demoaa/acl-user/login"
		);
	}



	/**
	 * 可参考：https://blog.csdn.net/lovely960823/article/details/117351830
	 * 用于在controller中实现登录接口，如：
	 * @Autowired
	 * 	private AuthenticationManager authenticationManager;
	 *
	 * 	@PostMapping("/login")
	 * 	public R login(@RequestBody User user) {
	 * 		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	 * 	    TokenLoginFilter过滤器逻辑（认证成功、失败逻辑）...............
	 * 		return R.ok().data("info", token);
	 * 	}
	 * 	此处不建议注入该bean（会导致UserDetailsServiceAutoConfiguration无法自动装配），因为此处spring_security被作为父工程被引入
	 *  若子工程中没有实现UserDetailService,启动将报错，可考虑在该类中定义登录方法用于controller调用
	 * @return
	 * @throws Exception
	 */
	/*@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}*/
}
