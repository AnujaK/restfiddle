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
package com.restfiddle.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ReportController {
    Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * This API can be used to generate report for "RUN PROJECT" functionality.
     */
    @RequestMapping(value = "/api/report/projects/{id}", method = RequestMethod.GET)
    public void generateRunProjectReport(@PathVariable("id") Long id) {

    }

    /**
     * This API can be used to generate REST API documentation for a project.
     */
    @RequestMapping(value = "/api/documentation/projects/{id}", method = RequestMethod.GET)
    public void generateProjectApiDocumentation(@PathVariable("id") Long id) {

    }

}
