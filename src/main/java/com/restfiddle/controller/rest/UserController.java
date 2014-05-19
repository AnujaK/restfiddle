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

import com.restfiddle.dao.UserRepository;
import com.restfiddle.dto.UserDTO;
import com.restfiddle.entity.User;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/api/users", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    User create(@RequestBody UserDTO userDTO) {
	logger.debug("Creating a new user with information: " + userDTO);

	User user = new User();

	user.setName(userDTO.getName());
	user.setDescription(userDTO.getDescription());

	return userRepository.save(user);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    User delete(@PathVariable("id") Long id) {
	logger.debug("Deleting user with id: " + id);

	User deleted = userRepository.findOne(id);

	userRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public @ResponseBody
    List<User> findAll() {
	logger.debug("Finding all users");

	return userRepository.findAll();
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    public @ResponseBody
    User findById(@PathVariable("id") Long id) {
	logger.debug("Finding user by id: " + id);

	return userRepository.findOne(id);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    User update(@PathVariable("id") Long id, @RequestBody UserDTO updated) {
	logger.debug("Updating user with information: " + updated);

	User user = userRepository.findOne(updated.getId());

	user.setName(updated.getName());
	user.setDescription(updated.getDescription());

	return user;
    }
}
