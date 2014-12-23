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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
	String reportTemplateFilePath = "report-template" + File.separator + "ApiDocumentationTemplate.jrxml";
	Resource resource = new ClassPathResource(reportTemplateFilePath);

	try (InputStream inputStream = resource.getInputStream();) {
	    JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
	    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

	    Map<String, Object> params = new HashMap<String, Object>();
	    JasperFillManager.fillReport(jasperReport, params);
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	} catch (JRException e) {
	    logger.error(e.getMessage(), e);
	}

    }

}
