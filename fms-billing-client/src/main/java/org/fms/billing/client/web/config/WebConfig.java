/**
 *    Auth:riozenc
 *    Date:2018年12月11日 下午4:49:31
 *    Title:security.web.WebConfig.java
 **/
package org.fms.billing.client.web.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.riozenc.titanTool.spring.web.client.TitanTemplate;

@Configuration
public class WebConfig implements WebMvcConfigurer {

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
