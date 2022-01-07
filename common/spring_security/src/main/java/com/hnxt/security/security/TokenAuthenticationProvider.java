package com.hnxt.security.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author yinz
 * @Date 2021/10/25 - 15:39
 * 此处不建议注入该bean（会导致UserDetailsServiceAutoConfiguration无法自动装配），因为此处spring_security被作为父工程被引入
 * 若子工程中没有实现UserDetailService,启动将报错
 */

public class TokenAuthenticationProvider implements AuthenticationProvider {


	private UserDetailsService userDetailsService;

	private PasswordEncoder passwordEncoder;

	public TokenAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (passwordEncoder.matches(password, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
		} else {
			throw new BadCredentialsException("The username or password is wrong!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
