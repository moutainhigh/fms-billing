package org.fms.billing.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "org.fms", exclude = MongoAutoConfiguration.class)
public class App {
	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}
}