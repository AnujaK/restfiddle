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

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.util.ConversationConverter;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.dto.StatusResponse;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.exceptions.ApiException;
import com.restfiddle.handler.RequestHandler;
import com.restfiddle.handler.http.GenericHandler;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    RequestHandler requestHandler;

    @Resource
    private ProjectController projectController;
    
    @Resource
    private NodeRepository nodeRepository;
    
    @Resource
    private ConversationRepository conversationRepository;

    @RequestMapping(value = "/api/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    RfResponseDTO requestProcessor(@RequestBody RfRequestDTO rfRequestDTO) {
	try {
	    GenericHandler handler = requestHandler.getHandler(rfRequestDTO.getMethodType());
	    
	    long startTime = System.currentTimeMillis();
	    
	    RfResponseDTO result = handler.process(rfRequestDTO);
	    
	    long endTime = System.currentTimeMillis();
	    
	    long duration = endTime - startTime;

	    Conversation item = ConversationConverter.convertToEntity(rfRequestDTO, result);
	    
	    item.setDuration(duration);

	    // TODO : Support all the databases.
	    // TODO : Use Item controller here.
	    try {
		conversationRepository.save(item);
	    } catch (InvalidDataAccessResourceUsageException e) {
		throw new ApiException("Please use sql as datasource, some of features are not supported by hsql", e);
	    }
	    return result;

	} catch (IOException e) {
	    logger.error("IO Exception", e);
	    throw new ApiException(e);
	}
    }
    
    @RequestMapping(value = "/api/processor/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody
    StatusResponse projectRunner(@PathVariable("id") Long id) {
	logger.debug("Running all requests inside project : " + id);

	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);
	for (BaseNode baseNode : listOfNodes) {
	    String nodeType = baseNode.getNodeType();
	    if (nodeType != null && (NodeType.PROJECT.name().equals(nodeType) || NodeType.FOLDER.name().equals(nodeType))) {
		continue;
	    } else {
		Conversation conversation = baseNode.getConversation();
		if (conversation != null && conversation.getRfRequest() != null) {
		    RfRequest rfRequest = conversation.getRfRequest();
		    String methodType = rfRequest.getMethodType();
		    String apiUrl = rfRequest.getApiUrl();
		    String apiBody = rfRequest.getApiBody();
		    if (methodType != null && !methodType.isEmpty() && apiUrl != null && !apiUrl.isEmpty()) {
			RfRequestDTO rfRequestDTO = new RfRequestDTO();
			rfRequestDTO.setMethodType(methodType);
			rfRequestDTO.setApiUrl(apiUrl);
			rfRequestDTO.setApiBody(apiBody);
			RfResponseDTO rfResponseDTO = requestProcessor(rfRequestDTO);
			logger.debug(baseNode.getName() + " ran with status : " + rfResponseDTO.getStatus());
		    }
		}
	    }

	}
	StatusResponse res = new StatusResponse();
	res.setStatus(HttpStatus.OK.name());
	return res;
    }
}
