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
 * See the License for the specific language governing runner/logs and
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

import com.restfiddle.dao.RunnerLogRepository;
import com.restfiddle.dto.RunnerLogDTO;
import com.restfiddle.entity.RunnerLog;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class RunnerLogController {
    Logger logger = LoggerFactory.getLogger(RunnerLogController.class);

    @Resource
    private RunnerLogRepository runnerLogRepository;

    @RequestMapping(value = "/api/runner/logs", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    RunnerLog create(@RequestBody RunnerLogDTO runnerLogDTO) {
	logger.debug("Creating a new runnerLog with information: " + runnerLogDTO);

	RunnerLog runnerLog = new RunnerLog();

	runnerLog.setName(runnerLogDTO.getName());
	runnerLog.setDescription(runnerLogDTO.getDescription());

	runnerLog.setNodeId(runnerLogDTO.getNodeId());

	return runnerLogRepository.save(runnerLog);
    }

    @RequestMapping(value = "/api/runner/logs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    RunnerLog delete(@PathVariable("id") String id) {
	logger.debug("Deleting runnerLog with id: " + id);

	RunnerLog deleted = runnerLogRepository.findOne(id);

	runnerLogRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/runner/logs", method = RequestMethod.GET)
    public @ResponseBody
    List<RunnerLog> findAll() {
	logger.debug("Finding all runner/logs");

	return runnerLogRepository.findAll();
    }

    @RequestMapping(value = "/api/runner/logs/{id}", method = RequestMethod.GET)
    public @ResponseBody
    RunnerLog findById(@PathVariable("id") String id) {
	logger.debug("Finding runnerLog by id: " + id);

	return runnerLogRepository.findOne(id);
    }

    @RequestMapping(value = "/api/runner/logs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    RunnerLog update(@PathVariable("id") Long id, @RequestBody RunnerLogDTO updated) {
	logger.debug("Updating runnerLog with information: " + updated);

	RunnerLog runnerLog = runnerLogRepository.findOne(updated.getId());

	runnerLog.setName(updated.getName());
	runnerLog.setDescription(updated.getDescription());

	return runnerLog;
    }
}
