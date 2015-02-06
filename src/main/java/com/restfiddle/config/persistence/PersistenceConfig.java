/*
 * Copyright 2014 Ranjan Kumar
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
package com.restfiddle.config.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.restfiddle.entity.User;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = { "com.restfiddle.dao" })
@EnableMongoAuditing
public class PersistenceConfig {

    @Autowired
    private Environment env;

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
	UserCredentials userCredentials = new UserCredentials(env.getProperty("mongodb.username"), env.getProperty("mongodb.password"));
	return new SimpleMongoDbFactory(new MongoClient(env.getProperty("mongodb.host"), env.getProperty("mongodb.port", Integer.class)),
		env.getProperty("mongodb.name"), userCredentials);
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
	return new MongoTemplate(mongoDbFactory());
    }
    
    @Bean
    public AuditorAware<User> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
