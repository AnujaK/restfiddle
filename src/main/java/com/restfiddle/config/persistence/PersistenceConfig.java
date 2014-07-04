package com.restfiddle.config.persistence;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
