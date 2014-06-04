/*
 * Copyright 2014 Santosh Mishra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    static @Bean
    public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
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
    @PropertySource("classpath:env-dev.properties")
    static class Test {
    }
}
