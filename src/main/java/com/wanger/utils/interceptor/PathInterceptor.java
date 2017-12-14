package com.wanger.utils.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 定义拦截器
 * @author wangjieya
 *
 */
public class PathInterceptor extends HandlerInterceptorAdapter {

	 public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
	        String path = httpServletRequest.getContextPath();
	        String scheme = httpServletRequest.getScheme();
	        String serverName = httpServletRequest.getServerName();
	        int port = httpServletRequest.getServerPort();
	        String basePath = scheme + "://" + serverName + ":" + port + path;
	        httpServletRequest.setAttribute("basePath", basePath);//前台取项目路径就是${basePath}
	        return true;
	    }

	    @Override
	    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	    }

	    @Override
	    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	    }
}
