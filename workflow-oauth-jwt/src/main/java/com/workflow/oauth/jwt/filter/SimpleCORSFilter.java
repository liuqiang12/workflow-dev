package com.workflow.oauth.jwt.filter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
/**
 * 跨域请求过滤器
 */
public class SimpleCORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		String originHeader = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", originHeader);//指定Origin域名才能访问
		response.setHeader("Access-Control-Allow-Credentials", "true");//允许发送cookies到服务器
		response.setHeader("Access-Control-Allow-Methods", "*");//响应类型 POST.GET.DELETE.PUT等
		response.setHeader("Access-Control-Max-Age", "3600");//本次预检请求的有效期，单位为秒:一个小时
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
		
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	        response.setStatus(HttpServletResponse.SC_OK);
	    } else {
	        chain.doFilter(req, res);
	    }
		
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}
