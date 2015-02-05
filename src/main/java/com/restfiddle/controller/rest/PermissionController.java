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

import com.restfiddle.dao.PermissionRepository;
import com.restfiddle.dto.PermissionDTO;
import com.restfiddle.entity.Permission;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class PermissionController {
    Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Resource
    private PermissionRepository permissionRepository;

    @RequestMapping(value = "/api/permissions", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Permission create(@RequestBody PermissionDTO permissionDTO) {
	logger.debug("Creating a new permission with information: " + permissionDTO);

	Permission permission = new Permission();
	permission.setType(permissionDTO.getType());
	permission.setName(permissionDTO.getName());
	permission.setDescription(permissionDTO.getDescription());

	return permissionRepository.save(permission);
    }

    @RequestMapping(value = "/api/permissions/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Permission delete(@PathVariable("id") String id) {
	logger.debug("Deleting permission with id: " + id);

	Permission deleted = permissionRepository.findOne(id);

	permissionRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    public @ResponseBody
    List<Permission> findAll() {
	logger.debug("Finding all permissions");

	return permissionRepository.findAll();
    }

    @RequestMapping(value = "/api/permissions/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Permission findById(@PathVariable("id") String id) {
	logger.debug("Finding permission by id: " + id);

	return permissionRepository.findOne(id);
    }

    @RequestMapping(value = "/api/permissions/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Permission update(@PathVariable("id") Long id, @RequestBody PermissionDTO updated) {
	logger.debug("Updating permission with information: " + updated);

	Permission permission = permissionRepository.findOne(updated.getId());

	permission.setName(updated.getName());
	permission.setDescription(updated.getDescription());

	return permission;
    }
}
