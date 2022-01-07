package com.hnxt.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnxt.common.util.ResponseUtil;
import com.hnxt.common.vo.R;
import com.hnxt.security.entity.SecurityUser;
import com.hnxt.security.entity.User;
import com.hnxt.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  * <p>
 *  * 登录过滤器，继承UsernamePasswordAuthenticationFilter
 *  仅拦截处理登录请求（其他请求直接放行），此处登录路径为：/admin/acl/login（构造参数中指定）
 *  该过滤器可不配置，登录接口可在controller中实现，对应的配置类需做微调
 *  * </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:30
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
	private TokenManager tokenManager;
	private RedisTemplate redisTemplate;

	public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
		setAuthenticationManager(authenticationManager);
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
		this.setPostOnly(false);
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			//接收前台传过来的登录参数
			User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			this.setDetails(req, userToken);
			return this.getAuthenticationManager().authenticate(userToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 登录成功
	 * 可查看父类AbstractAuthenticationProcessingFilter的该方法，通过配置successHandler实现相同的功能
	 * @param req
	 * @param res
	 * @param chain
	 * @param auth
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(auth);

		SecurityUser user = (SecurityUser) auth.getPrincipal();
		String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
		redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());

		ResponseUtil.out(res, R.ok().data("token", token));
	}

	/**
	 * 登录失败
	 * 可查看父类AbstractAuthenticationProcessingFilter的该方法，通过配置failureHandler实现相同的功能
	 * @param request
	 * @param response
	 * @param e
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException e) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		ResponseUtil.out(response, R.error().message("认证失败"));
	}
}
