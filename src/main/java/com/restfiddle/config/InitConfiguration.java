package com.restfiddle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.restfiddle.boot.Initializer;

/**
 * @author abidk
 * 
 */
@Configuration
@ComponentScan(basePackages = "com.restfiddle")
public class InitConfiguration {

	@Bean
	public Initializer initializer() {
		return new Initializer();
	}
}
