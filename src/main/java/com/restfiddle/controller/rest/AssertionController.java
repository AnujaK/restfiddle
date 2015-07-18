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
 * See the License for the specific language governing tags and
 * limitations under the License.
 */
package com.restfiddle.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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

import com.restfiddle.dao.AssertionRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.dto.AssertionDTO;
import com.restfiddle.dto.BodyAssertDTO;
import com.restfiddle.entity.Assertion;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.BodyAssert;
import com.restfiddle.entity.RfRequest;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class AssertionController {
    Logger logger = LoggerFactory.getLogger(AssertionController.class);
    
    @Autowired
    private NodeRepository nodeRepository;

    @Resource
    private RfRequestRepository rfRequestRepository;
    
    @Resource
    private AssertionRepository assertionRepository;

    @RequestMapping(value = "/api/requests/{nodeId}/asserts", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Assertion save(@PathVariable("nodeId") String nodeId, @RequestBody AssertionDTO assertionDTO) {
	
	BaseNode node = nodeRepository.findOne(nodeId);
	if(node == null || node.getConversation() == null){
	    return null;
	}
	
	RfRequest rfRequest = node.getConversation().getRfRequest();
	if (rfRequest == null) {
	    return null;
	}

	Assertion assertion = new Assertion();
	List<BodyAssertDTO> bodyAssertDTOs = assertionDTO.getBodyAssertDTOs();

	if (bodyAssertDTOs != null && !bodyAssertDTOs.isEmpty()) {
	    List<BodyAssert> bodyAsserts = new ArrayList<BodyAssert>();
	    BodyAssert bodyAssert = null;
	    for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
		bodyAssert = new BodyAssert();
		bodyAssert.setPropertyName(bodyAssertDTO.getPropertyName());
		bodyAssert.setComparator(bodyAssertDTO.getComparator());
		bodyAssert.setExpectedValue(bodyAssertDTO.getExpectedValue());
		bodyAsserts.add(bodyAssert);
	    }
	    assertion.setBodyAsserts(bodyAsserts);
	    rfRequest.setAssertion(assertion);
	}
	
	assertion = assertionRepository.save(assertion);

	RfRequest savedRFRequest = rfRequestRepository.save(rfRequest);
	return savedRFRequest.getAssertion();
    }

    @RequestMapping(value = "/api/requests/{nodeId}/asserts", method = RequestMethod.GET)
    public @ResponseBody
    Assertion findAsserts(@PathVariable("nodeId") String nodeId) {
	BaseNode node = nodeRepository.findOne(nodeId);
	if(node == null || node.getConversation() == null){
	    return null;
	}
	
	RfRequest rfRequest = node.getConversation().getRfRequest();
	if (rfRequest == null) {
	    return null;
	}
	
	Assertion assertion = rfRequest.getAssertion();
	return assertion;
    }

}
