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

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories("com.restfiddle.dao")
public class PersistenceConfig extends AbstractMongoConfiguration{

    @Autowired
    private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongodb.name");
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
	    return new MongoClient(Collections.singletonList(new ServerAddress(env.getProperty("mongodb.host"), env.getProperty("mongodb.port", Integer.class))),
		    Collections.singletonList(MongoCredential.createCredential(env.getProperty("mongodb.username"), env.getProperty("mongodb.name"), env.getProperty("mongodb.password").toCharArray())));
	}
	
	  @Override
	  protected String getMappingBasePackage() {
	    return "com.restfiddle.dao";
	  }
}
