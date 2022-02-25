package com.flowable.flowableboot;

import com.flowable.flowableboot.service.FlowableService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(proxyBeanMethods = false)
public class FlowablebootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowablebootApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(final FlowableService flowableService) {
		return strings -> flowableService.createDemoUsers();
	}
}

