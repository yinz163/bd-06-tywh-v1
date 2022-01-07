package com.hnxt.security.security;

import com.hnxt.common.util.ResponseUtil;
import com.hnxt.common.vo.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未授权异常处理
 * @Author yinz
 * @Date 2021/10/25 - 11:00
 */
public class UnAuthorizedEntryPoint implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
		ResponseUtil.out(httpServletResponse, R.error().message("未授权禁止访问"));
	}
}
