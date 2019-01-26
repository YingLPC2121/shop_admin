package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.AdminInfo;


public class SessionFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String contextPath = req.getContextPath(); // /shop_admin
		String uri = req.getRequestURI();
		//判断发送过来的url是不是符合的
		if(uri.indexOf("login.jsp")>-1){
			chain.doFilter(request, response);						
		}else{
			//判断session是否存在
			AdminInfo adminInfo = (AdminInfo)req.getSession().getAttribute("adminInfo");
			if(adminInfo!=null){
				//存在，放行
				chain.doFilter(request, response);
			}else{
				//不存在,直接重定向到登陆页面
				resp.sendRedirect(contextPath+"/login.jsp");
			}
		}
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
