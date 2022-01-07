package com.hnxt.service.exception.handler;

import com.hnxt.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @Author yinz
 * @Date 2021/8/30 - 17:02
 */
@RestControllerAdvice
@Slf4j
/*@ConditionalOnMissingClass(value = {"org.springframework.security.access.AccessDeniedException",
		"org.springframework.security.core.AuthenticationException"})*/
public class GlobalErrorHandler {

	/**
	 * 检查是否ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return request.getHeader("X-Requested-With") != null
				&&"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	/**
	 * 全局异常处理
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception{
		log.error("request url【{}】", request.getRequestURI());
//		log.error(request.getRequestURI(), ex);
		String msg = generateErrorMsg(ex);
		if(isAjax(request)) {
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
	}

	public static String generateErrorMsg(Exception ex) {
		String msg = ex.getMessage();
		if(ex instanceof MethodArgumentNotValidException) {
			//@RequestBody(对象类型参数校验)
			MethodArgumentNotValidException me = (MethodArgumentNotValidException) ex;
			msg = handleFieldError(me.getBindingResult().getFieldErrors());
		} else if(ex instanceof ConstraintViolationException) {
			//@RequestParam @PathVariable(基本数据类型参数校验)
			ConstraintViolationException ce = (ConstraintViolationException) ex;
			msg = handleConstraint(ce.getConstraintViolations());
		} else if(ex instanceof BindException) {
			//@RequestParam(对象类型参数校验)
//			List<FieldError> allErrors = ((BindException) ex).getFieldErrors();
//			msg = handleFieldError(allErrors);
		}
		return msg;
	}

	private static  String handleFieldError(List<FieldError> fieldErrors) {
		StringBuilder sb = new StringBuilder();
		for(FieldError obj : fieldErrors) {
			sb.append(obj.getField()).append("=[").append(obj.getDefaultMessage()).append("] \n");
		}
		return sb.toString();
	}

	private static String handleConstraint(Set<ConstraintViolation<?>> cvs) {
		StringBuilder sb = new StringBuilder();
		for(ConstraintViolation<?> obj : cvs) {
			String path = obj.getPropertyPath().toString();
			String field = path.substring(path.lastIndexOf(".") + 1);
			sb.append(field).append("=[").append(obj.getMessage()).append("] ");
		}
		return sb.toString();
	}
}
