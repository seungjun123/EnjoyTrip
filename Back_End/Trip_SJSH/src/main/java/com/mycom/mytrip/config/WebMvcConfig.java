package com.mycom.mytrip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
//	@Autowired
//	LoginInterceptor loginInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(loginInterceptor)
//				.addPathPatterns("/**")
//				.excludePathPatterns(
//						"/",
//						"/login/**",
//						"/users/**",
//						"/codes/**",
//						"/css/**",
//						"/js/**",
//						"/img/**",
//						"/favicon.ico"
//						);
//		
//		
//	}
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.1.5:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000);
    }
}
