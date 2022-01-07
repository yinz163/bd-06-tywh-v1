package com.hnxt.security.filter;

import com.hnxt.common.util.ResponseUtil;
import com.hnxt.common.vo.R;
import com.hnxt.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  <p>
 *  访问过滤器，用于校验token校验用户是否已登录认证
 *  </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:28
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
	private TokenManager tokenManager;
	private RedisTemplate redisTemplate;

	public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
		super(authManager);
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.info("================="+req.getRequestURI());
		/*if(req.getRequestURI().indexOf("admin") == -1) {
			chain.doFilter(req, res);
			return;
		}*/

		UsernamePasswordAuthenticationToken authentication = null;
		try {
			authentication = getAuthentication(req);
		} catch (Exception e) {
			ResponseUtil.out(res, R.error().message("token不合法"));
			return;
		}

		if (authentication != null) {
			//将用户信息、权限放入threadlocal共享,可用于实现获取当前登录用户，此处可尝试登录成功后直接缓存authentication对象，
			// 这样就不需要每次请求都构造authentication
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			ResponseUtil.out(res, R.error().message("未认证用户无法访问"));
			return;
		}
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		// token置于header里
		String token = request.getHeader("token");
		if (token != null && !"".equals(token.trim())) {
			String userName = tokenManager.getUserFromToken(token);

			List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			for(String permissionValue : permissionValueList) {
				if(StringUtils.isEmpty(permissionValue)) continue;
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
				authorities.add(authority);
			}

			if (!StringUtils.isEmpty(userName)) {
				return new UsernamePasswordAuthenticationToken(userName, token, authorities);
			}
			return null;
		}
		return null;
	}
}
