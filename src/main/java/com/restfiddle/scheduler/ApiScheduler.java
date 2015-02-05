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
package com.restfiddle.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.restfiddle.controller.rest.ApiController;
import com.restfiddle.mail.MailSender;

@Component
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@Transactional
public class ApiScheduler {
    Logger logger = LoggerFactory.getLogger(ApiScheduler.class);

    @Autowired
    MailSender mailSender;

    @Autowired
    ApiController apiController;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // @Scheduled(cron = "${cron.expression}")
    public void runSelectedProjects() {
	//apiController.runProjectById(1l);
    }

    @Scheduled(cron = "${cron.expression}")
    public void reportCurrentTime() {
	logger.debug("The time is now " + dateFormat.format(new Date()));
    }
}
