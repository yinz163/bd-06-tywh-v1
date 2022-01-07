package com.hnxt.security.security;

import com.hnxt.common.util.ResponseUtil;
import com.hnxt.common.vo.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  * <p>
 *  * 未登录的统一处理方式
 *  * AccessDeniedHandler：未授权处理类
 *  * </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:33
 */
public class UnauthenticatedEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {

		ResponseUtil.out(response, R.error().message("未认证禁止访问"));
	}
}
