package com.hnxt.service.config.feign;

import com.hnxt.common.util.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author yinz
 * @Date 2021/9/15 - 9:40
 */
@Configuration
public class FeignHeadersInterceptor implements RequestInterceptor {

	/**
	 * 通过openfeign进行微服务间调用，保持登录token传递
	 * @param requestTemplate
	 */
	@Override
	public void apply(RequestTemplate requestTemplate) {
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		if(request == null) {
			return;
		}
		Map<String, String> headers = RequestUtil.getHeaders(request);
		if(headers.size() > 0) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				requestTemplate.header(entry.getKey(), entry.getValue());
			}
		}
	}
}
