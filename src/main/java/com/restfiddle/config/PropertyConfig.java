package com.restfiddle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by santoshm1 on 04/06/14.
 * 
 * Adds support for runtime property files. Run with -Dspring.profiles.active={production,default,development,test} defaults to development.
 * 
 */

@Configuration
@PropertySource({ "classpath:common.properties" })
public class PropertyConfig {

    private PropertyConfig() {} 

    @Bean
    public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Properties to support the 'production' mode of operation.
     */
    @Configuration
    @Profile("production")
    @PropertySource("classpath:env-production.properties")
    static class Production {
	// Define additional beans for this profile here
    }

    /**
     * Properties to support the 'test' mode of operation.
     */
    @Configuration
    @Profile({ "devlopment", "default" })
    @PropertySource("classpath:env-development.properties")
    static class Dev {
    }

    /**
     * Properties to support the 'test' mode of operation.
     */
    @Configuration
    @Profile("test")
    @PropertySource("classpath:env-test.properties")
    static class Test {
    }
}
