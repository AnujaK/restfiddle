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
 * See the License for the specific language governing logs and
 * limitations under the License.
 */
package com.restfiddle.controller.rest;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ActivityLogRepository;
import com.restfiddle.dto.ActivityLogDTO;
import com.restfiddle.entity.ActivityLog;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ActivityLogController {
    Logger logger = LoggerFactory.getLogger(ActivityLogController.class);

    @Resource
    private ActivityLogRepository activityLogRepository;

    @RequestMapping(value = "/api/logs", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ActivityLog create(@RequestBody ActivityLogDTO activityLogDTO) {
	logger.debug("Creating a new activityLog with information: " + activityLogDTO);

	ActivityLog activityLog = new ActivityLog();
	activityLog.setName(activityLogDTO.getName());
	activityLog.setDescription(activityLogDTO.getDescription());
	return activityLogRepository.save(activityLog);
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    ActivityLog delete(@PathVariable("id") Long id) {
	logger.debug("Deleting activityLog with id: " + id);

	ActivityLog deleted = activityLogRepository.findOne(id);

	activityLogRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/logs", method = RequestMethod.GET)
    public @ResponseBody
    List<ActivityLog> findAll() {
	logger.debug("Finding all logs");

	return activityLogRepository.findAll();
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ActivityLog findById(@PathVariable("id") Long id) {
	logger.debug("Finding activityLog by id: " + id);

	return activityLogRepository.findOne(id);
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    ActivityLog update(@PathVariable("id") Long id, @RequestBody ActivityLogDTO updated) {
	logger.debug("Updating activityLog with information: " + updated);

	ActivityLog activityLog = activityLogRepository.findOne(updated.getId());

	activityLog.setName(updated.getName());
	activityLog.setDescription(updated.getDescription());

	return activityLog;
    }
}
