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
package com.restfiddle.config.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sendgrid.SendGrid;

@Configuration
public class MailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender javaMailSender() {
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	Properties mailProperties = new Properties();

	mailSender.setProtocol(env.getProperty("mail.protocol"));
	mailSender.setHost(env.getProperty("mail.host"));
	mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));

	mailSender.setUsername(env.getProperty("mail.username"));
	mailSender.setPassword(env.getProperty("mail.password"));

	mailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
	mailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
	mailProperties.put("mail.smtp.debug", env.getProperty("mail.smtp.debug"));

	mailProperties.put("mail.smtp.socketFactory.port", "465");
	mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	mailProperties.put("mail.smtps.ssl.trust", env.getProperty("mail.smtps.ssl.trust"));
	mailProperties.put("mail.smtps.ssl.checkserveridentity", env.getProperty("mail.smtps.ssl.checkserveridentity"));

	mailSender.setJavaMailProperties(mailProperties);

	return mailSender;
    }

    @Bean
    public SendGrid sendGrid() {
	SendGrid sendgrid = new SendGrid(env.getProperty("sendgrid.username"), env.getProperty("sendgrid.password"));
	return sendgrid;
    }
}
