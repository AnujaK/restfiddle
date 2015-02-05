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

import com.restfiddle.dao.ModuleRepository;
import com.restfiddle.dto.ModuleDTO;
import com.restfiddle.entity.Module;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ModuleController {
    Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Resource
    private ModuleRepository moduleRepository;

    @RequestMapping(value = "/api/modules", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Module create(@RequestBody ModuleDTO moduleDTO) {
	logger.debug("Creating a new module with information: " + moduleDTO);

	Module module = new Module();

	module.setName(moduleDTO.getName());
	module.setDescription(moduleDTO.getDescription());

	return moduleRepository.save(module);
    }

    @RequestMapping(value = "/api/modules/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Module delete(@PathVariable("id") String id) {
	logger.debug("Deleting module with id: " + id);

	Module deleted = moduleRepository.findOne(id);

	moduleRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/modules", method = RequestMethod.GET)
    public @ResponseBody
    List<Module> findAll() {
	logger.debug("Finding all modules");

	return moduleRepository.findAll();
    }

    @RequestMapping(value = "/api/modules/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Module findById(@PathVariable("id") String id) {
	logger.debug("Finding module by id: " + id);

	return moduleRepository.findOne(id);
    }

    @RequestMapping(value = "/api/modules/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Module update(@PathVariable("id") String id, @RequestBody ModuleDTO updated) {
	logger.debug("Updating module with information: " + updated);

	Module module = moduleRepository.findOne(updated.getId());

	module.setName(updated.getName());
	module.setDescription(updated.getDescription());

	return module;
    }
}
