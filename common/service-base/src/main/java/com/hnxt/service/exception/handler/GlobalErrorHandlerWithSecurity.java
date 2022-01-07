package com.hnxt.service.exception.handler;

import com.hnxt.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @Author yinz
 * @Date 2021/10/29 - 8:58
 */
@RestControllerAdvice
@Slf4j
@ConditionalOnClass(value = {AccessDeniedException.class, AuthenticationException.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalErrorHandlerWithSecurity {


	@ExceptionHandler(value = {AccessDeniedException.class, AuthenticationException.class})
	public Object handleSecurityException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
		//不处理security的未认证和未授权异常,交由security过滤器自行处置
		throw ex;
	}
/*	*//**
	 * 全局异常处理
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 * @throws Exception
	 *//*
	@ExceptionHandler(Exception.class)
	public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception{
		log.error("request url【{}】", request.getRequestURI());
		log.error(request.getRequestURI(), ex);
		String msg = GlobalErrorHandler.generateErrorMsg(ex);
		if(GlobalErrorHandler.isAjax(request)) {
			return R.error().message(msg);
		} else {
			ModelAndView mv = new ModelAndView();
			mv.addObject("exception", ex);
			mv.addObject("url", request.getRequestURL());
			mv.addObject("msg", msg);
			mv.addObject("code", 0);
			mv.setViewName("error");
			return mv;
		}
	}*/
}
