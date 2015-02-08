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

import com.restfiddle.dao.OAuth2Repository;
import com.restfiddle.dto.OAuth2RequestDTO;
import com.restfiddle.entity.OAuth2;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class OAuth2Controller {
    Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @Resource
    private OAuth2Repository oauth2Repository;

    @RequestMapping(value = "/api/oauth2", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    OAuth2 create(@RequestBody OAuth2RequestDTO OAuth2RequestDTO) {
	logger.debug("Creating a new oauth2 with information: " + OAuth2RequestDTO);

	OAuth2 oauth2 = new OAuth2();
	List<String> oauth2ScopeList = OAuth2RequestDTO.getScopes();
	String scope = "";

	if (oauth2ScopeList != null && oauth2ScopeList.size() != 0) {
	    for (int i = 0; i < oauth2ScopeList.size() - 1; i++) {
		scope += oauth2ScopeList.get(i);
		scope += ", ";
	    }
	    scope += oauth2ScopeList.get(oauth2ScopeList.size() - 1);
	}

	oauth2.setName(OAuth2RequestDTO.getName());
	oauth2.setDescription(OAuth2RequestDTO.getDescription());

	oauth2.setAccessTokenLocation(OAuth2RequestDTO.getAccessTokenLocation());
	oauth2.setAccessTokenUrl(OAuth2RequestDTO.getAccessTokenUrl());
	oauth2.setAuthorizationUrl(OAuth2RequestDTO.getAuthorizationUrl());
	oauth2.setClientId(OAuth2RequestDTO.getClientId());
	oauth2.setClientSecret(OAuth2RequestDTO.getClientSecret());
	oauth2.setScopes(scope);
	return oauth2Repository.save(oauth2);
    }

    @RequestMapping(value = "/api/oauth2/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    void delete(@PathVariable("id") String id) {
	logger.debug("Deleting oauth2 with id: " + id);

	OAuth2 deleted = oauth2Repository.findOne(id);

	oauth2Repository.delete(deleted);
    }

    @RequestMapping(value = "/api/oauth2", method = RequestMethod.GET)
    public @ResponseBody
    List<OAuth2> findAll() {
	logger.debug("Finding all oauth2");

	return oauth2Repository.findAll();
    }

    @RequestMapping(value = "/api/oauth2/{id}", method = RequestMethod.GET)
    public @ResponseBody
    OAuth2 findById(@PathVariable("id") String id) {
	logger.debug("Finding oauth2 by id: " + id);

	return oauth2Repository.findOne(id);
    }

    @RequestMapping(value = "/api/oauth2/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    OAuth2 update(@PathVariable("id") Long id, @RequestBody OAuth2RequestDTO updated) {
	logger.debug("Updating oauth2 with information: " + updated);

	OAuth2 oauth2 = oauth2Repository.findOne(updated.getId());

	// oauth2.setName(updated.getName());
	// oauth2.setDescription(updated.getDescription());

	return oauth2;
    }

}