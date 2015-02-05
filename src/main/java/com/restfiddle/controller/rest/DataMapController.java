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
 * See the License for the specific language governing dataMaps and
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

import com.restfiddle.dao.DataMapRepository;
import com.restfiddle.dto.DataMapDTO;
import com.restfiddle.entity.DataMap;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class DataMapController {
    Logger logger = LoggerFactory.getLogger(DataMapController.class);

    @Resource
    private DataMapRepository dataMapRepository;

    @RequestMapping(value = "/api/dataMaps", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    DataMap create(@RequestBody DataMapDTO dataMapDTO) {
	logger.debug("Creating a new dataMap with information: " + dataMapDTO);

	DataMap dataMap = new DataMap();
	dataMap.setDataKey(dataMapDTO.getKey());
	dataMap.setValue(dataMapDTO.getValue());
	dataMap.setType(dataMapDTO.getType());
	return dataMapRepository.save(dataMap);
    }

    @RequestMapping(value = "/api/dataMaps/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    DataMap delete(@PathVariable("id") String id) {
	logger.debug("Deleting dataMap with id: " + id);

	DataMap deleted = dataMapRepository.findOne(id);

	dataMapRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/dataMaps", method = RequestMethod.GET)
    public @ResponseBody
    List<DataMap> findAll() {
	logger.debug("Finding all dataMaps");

	return dataMapRepository.findAll();
    }

    @RequestMapping(value = "/api/dataMaps/{id}", method = RequestMethod.GET)
    public @ResponseBody
    DataMap findById(@PathVariable("id") String id) {
	logger.debug("Finding dataMap by id: " + id);

	return dataMapRepository.findOne(id);
    }

    @RequestMapping(value = "/api/dataMaps/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    DataMap update(@PathVariable("id") Long id, @RequestBody DataMapDTO updated) {
	logger.debug("Updating dataMap with information: " + updated);

	DataMap dataMap = dataMapRepository.findOne(updated.getId());

	dataMap.setDataKey(updated.getKey());
	dataMap.setValue(updated.getValue());

	return dataMap;
    }
}
