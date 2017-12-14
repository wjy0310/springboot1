package com.wanger.utils.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 * 
 * @author wangjieya
 *
 */
@Configuration
public class PathInterceptorConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 将拦截器注册到path中
		registry.addInterceptor(new PathInterceptor()).addPathPatterns("/**");
	}
	
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		//将静态资源路径注册(前端可以当作根目录使用)
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/");
//        super.addResourceHandlers(registry);
//    }
}
