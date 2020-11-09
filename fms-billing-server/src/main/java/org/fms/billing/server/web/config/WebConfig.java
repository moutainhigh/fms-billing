/**
 *    Auth:riozenc
 *    Date:2018年12月11日 下午4:49:31
 *    Title:security.web.WebConfig.java
 **/
package org.fms.billing.server.web.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.fms.billing.server.web.interceptor.BaseInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.riozenc.titanTool.spring.context.SpringContextHolder;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/*");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		converters.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//		converters.add(2, new MessageConverter());
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public TitanTemplate titanTemplate(RestTemplate restTemplate) {
		return new TitanTemplate(restTemplate);

	}

}
