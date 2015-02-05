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

import com.restfiddle.dao.RoleRepository;
import com.restfiddle.dto.RoleDTO;
import com.restfiddle.entity.Role;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleRepository roleRepository;

    @RequestMapping(value = "/api/roles", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Role create(@RequestBody RoleDTO roleDTO) {
	logger.debug("Creating a new role with information: " + roleDTO);

	Role role = new Role();
	role.setType(roleDTO.getType());
	role.setName(roleDTO.getName());
	role.setDescription(roleDTO.getDescription());

	return roleRepository.save(role);
    }

    @RequestMapping(value = "/api/roles/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Role delete(@PathVariable("id") String id) {
	logger.debug("Deleting role with id: " + id);

	Role deleted = roleRepository.findOne(id);

	roleRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/roles", method = RequestMethod.GET)
    public @ResponseBody
    List<Role> findAll() {
	logger.debug("Finding all roles");

	return roleRepository.findAll();
    }

    @RequestMapping(value = "/api/roles/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Role findById(@PathVariable("id") String id) {
	logger.debug("Finding role by id: " + id);

	return roleRepository.findOne(id);
    }

    @RequestMapping(value = "/api/roles/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Role update(@PathVariable("id") String id, @RequestBody RoleDTO updated) {
	logger.debug("Updating role with information: " + updated);

	Role role = roleRepository.findOne(updated.getId());

	role.setName(updated.getName());
	role.setDescription(updated.getDescription());

	return role;
    }
}
