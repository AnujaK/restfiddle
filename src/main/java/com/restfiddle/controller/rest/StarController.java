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
 * See the License for the specific language governing stars and
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

import com.restfiddle.dao.StarRepository;
import com.restfiddle.dto.StarDTO;
import com.restfiddle.entity.Star;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
@Deprecated
public class StarController {
    Logger logger = LoggerFactory.getLogger(StarController.class);

    @Resource
    private StarRepository starRepository;

    @RequestMapping(value = "/api/stars", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Star create(@RequestBody StarDTO starDTO) {
	logger.debug("Creating a new star with information: " + starDTO);

	Star star = new Star();
	star.setName(starDTO.getName());
	star.setDescription(starDTO.getDescription());
	return starRepository.save(star);
    }

    @RequestMapping(value = "/api/stars/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Star delete(@PathVariable("id") String id) {
	logger.debug("Deleting star with id: " + id);

	Star deleted = starRepository.findOne(id);

	starRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/stars", method = RequestMethod.GET)
    public @ResponseBody
    List<Star> findAll() {
	logger.debug("Finding all stars");

	return starRepository.findAll();
    }

    @RequestMapping(value = "/api/stars/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Star findById(@PathVariable("id") String id) {
	logger.debug("Finding star by id: " + id);

	return starRepository.findOne(id);
    }

    @RequestMapping(value = "/api/stars/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Star update(@PathVariable("id") Long id, @RequestBody StarDTO updated) {
	logger.debug("Updating star with information: " + updated);

	Star star = starRepository.findOne(updated.getId());

	star.setName(updated.getName());
	star.setDescription(updated.getDescription());

	return star;
    }
}
