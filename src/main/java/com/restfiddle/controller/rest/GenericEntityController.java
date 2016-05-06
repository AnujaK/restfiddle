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
 * See the License for the specific language governing entities and
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.GenericEntityRepository;
import com.restfiddle.dto.GenericEntityDTO;
import com.restfiddle.dto.GenericEntityFieldDTO;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.GenericEntityField;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class GenericEntityController {
    Logger logger = LoggerFactory.getLogger(GenericEntityController.class);

    @Autowired
    private GenericEntityRepository genericEntityRepository;
    
    @Autowired
    private GenerateApiController generateApiController;

    @RequestMapping(value = "/api/entities", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity create(@RequestBody GenericEntityDTO genericEntityDTO) {
	logger.debug("Creating a new entity with information: " + genericEntityDTO);

	String entityName = genericEntityDTO.getName();
	
	GenericEntity entity = new GenericEntity();
	entity.setName(entityName);
	entity.setDescription(genericEntityDTO.getDescription());

	List<GenericEntityFieldDTO> fieldDTOs = genericEntityDTO.getFields();
	if (fieldDTOs != null && !fieldDTOs.isEmpty()) {
	    List<GenericEntityField> fields = new ArrayList<GenericEntityField>();
	    GenericEntityField field;
	    for (GenericEntityFieldDTO fieldDTO : fieldDTOs) {
		field = new GenericEntityField();
		field.setName(fieldDTO.getName());
		field.setType(fieldDTO.getType());
		fields.add(field);
	    }
	    entity.setFields(fields);
	}
	
	entity = genericEntityRepository.save(entity);
	return entity;
    }

    @RequestMapping(value = "/api/entities/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity delete(@PathVariable("id") String id) {
	logger.debug("Deleting entity with id: " + id);

	GenericEntity deleted = genericEntityRepository.findOne(id);

	genericEntityRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/entities", method = RequestMethod.GET)
    public @ResponseBody
    List<GenericEntity> findAll() {
	logger.debug("Finding all entities");

	return genericEntityRepository.findAll();
    }

    @RequestMapping(value = "/api/entities/{id}", method = RequestMethod.GET)
    public @ResponseBody
    GenericEntity findById(@PathVariable("id") String id) {
	logger.debug("Finding entity by id: " + id);

	return genericEntityRepository.findOne(id);
    }

    @RequestMapping(value = "/api/entities/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity update(@PathVariable("id") String id, @RequestBody GenericEntityDTO updated, @RequestParam(value = "generateApi", required = false) boolean generateApi) {
	logger.debug("Updating entity with information: " + updated);

	GenericEntity entity = genericEntityRepository.findOne(updated.getId());

	entity.setName(updated.getName());
	entity.setDescription(updated.getDescription());
	
	List<GenericEntityFieldDTO> fieldsDTOs = updated.getFields();
	if(fieldsDTOs != null){
	    List<GenericEntityField> fields = entity.getFields() != null ? entity.getFields(): new ArrayList<GenericEntityField>();
	    for(GenericEntityFieldDTO fieldDTO : fieldsDTOs){
		GenericEntityField field = new GenericEntityField();
		field.setName(fieldDTO.getName());
		field.setType(fieldDTO.getType());
		
		if(!fields.contains(field)){
		    fields.add(field);
		}
	    }
	    
	    entity.setFields(fields);
	}
	
	genericEntityRepository.save(entity);
	
	if(generateApi){
	    generateApiController.generateApiByEntity(entity);
	}

	return entity;
    }
}
