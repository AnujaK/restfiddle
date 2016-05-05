/*
 * Copyright 2015 Ranjan Kumar
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
 * See the License for the specific language governing environments and
 * limitations under the License.
 */
package com.restfiddle.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.EnvironmentRepository;
import com.restfiddle.dto.EnvironmentDTO;
import com.restfiddle.dto.EnvironmentPropertyDTO;
import com.restfiddle.entity.Environment;
import com.restfiddle.entity.EnvironmentProperty;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class EnvironmentController {
    Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    private EnvironmentRepository environmentRepository;

    @RequestMapping(value = "/api/environments", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Environment create(@RequestBody EnvironmentDTO environmentDTO) {
	logger.debug("Creating a new environment with information: " + environmentDTO);

	Environment environment = new Environment();
	environment.setName(environmentDTO.getName());
	environment.setDescription(environmentDTO.getDescription());

	List<EnvironmentPropertyDTO> propertyDTOs = environmentDTO.getProperties();
	if (propertyDTOs != null && !propertyDTOs.isEmpty()) {
	    List<EnvironmentProperty> properties = new ArrayList<EnvironmentProperty>();
	    EnvironmentProperty property;
	    for (EnvironmentPropertyDTO propertyDTO : propertyDTOs) {
		property = new EnvironmentProperty();
		property.setPropertyName(propertyDTO.getPropertyName());
		property.setPropertyValue(propertyDTO.getPropertyValue());
		properties.add(property);
	    }
	    environment.setProperties(properties);
	}

	environment = environmentRepository.save(environment);
	return environment;
    }

    @RequestMapping(value = "/api/environments/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Environment delete(@PathVariable("id") String id) {
	logger.debug("Deleting environment with id: " + id);

	Environment deleted = environmentRepository.findOne(id);

	environmentRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/environments", method = RequestMethod.GET)
    public @ResponseBody
    List<Environment> findAll() {
	logger.debug("Finding all environments");

	return environmentRepository.findAll();
    }

    @RequestMapping(value = "/api/environments/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Environment findById(@PathVariable("id") String id) {
	logger.debug("Finding environment by id: " + id);

	return environmentRepository.findOne(id);
    }

    @RequestMapping(value = "/api/environments/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Environment update(@PathVariable("id") String id, @RequestBody EnvironmentDTO updated) {
	logger.debug("Updating environment with information: " + updated);

	Environment environment = environmentRepository.findOne(updated.getId());

	environment.setName(updated.getName());
	environment.setDescription(updated.getDescription());

	List<EnvironmentPropertyDTO> propertyDTOs = updated.getProperties();
	if (propertyDTOs != null && !propertyDTOs.isEmpty()) {
	    List<EnvironmentProperty> properties = new ArrayList<EnvironmentProperty>();
	    EnvironmentProperty property;
	    for (EnvironmentPropertyDTO propertyDTO : propertyDTOs) {
		property = new EnvironmentProperty();
		property.setPropertyName(propertyDTO.getPropertyName());
		property.setPropertyValue(propertyDTO.getPropertyValue());
		properties.add(property);
	    }
	    environment.setProperties(properties);
	}
	environment = environmentRepository.save(environment);
	return environment;
    }
}
