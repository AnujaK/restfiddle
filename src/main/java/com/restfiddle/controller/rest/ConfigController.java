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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ConfigRepository;
import com.restfiddle.dto.ConfigDTO;
import com.restfiddle.entity.Config;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ConfigController {
    Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Resource
    private ConfigRepository configRepository;

    public @ResponseBody
    Config create(@RequestBody ConfigDTO configDTO) {
	logger.debug("Creating a new config with information: " + configDTO);

	Config config = new Config();

	config.setName(configDTO.getName());
	config.setDescription(configDTO.getDescription());

	config.setConfigKey(configDTO.getConfigKey());
	config.setConfigValue(configDTO.getConfigValue());

	return configRepository.save(config);
    }

    public @ResponseBody
    Config findById(String id) {
	logger.debug("Finding config by id: " + id);

	return configRepository.findOne(id);
    }

    public @ResponseBody
    List<Config> findAll() {
	return configRepository.findAll();
    }
}
