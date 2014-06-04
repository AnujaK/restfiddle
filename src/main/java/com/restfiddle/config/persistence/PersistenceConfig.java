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
package com.restfiddle.config.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by santoshm1 on 04/06/14. Moving persistence config out to decouple from Application booter.
 * 
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.restfiddle.dao" })
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource dataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName(env.getProperty("database.driver"));
	dataSource.setUrl(env.getProperty("database.url"));
	dataSource.setUsername(env.getProperty("database.username"));
	dataSource.setPassword(env.getProperty("database.password"));
	return dataSource;
    }
}
