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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
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
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.dao.util.ConversationConverter;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeStatusResponseDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.exceptions.ApiException;
import com.restfiddle.handler.http.GenericHandler;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    GenericHandler genericHandler;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private RfRequestRepository rfRequestRepository;

    @RequestMapping(value = "/api/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    RfResponseDTO requestProcessor(@RequestBody RfRequestDTO rfRequestDTO) {
	Conversation existingConversation = null;
	Conversation conversationForLogging = null;

	// TODO : Get RfRequest Id if present as part of this request and update the existing conversation entity.
	// Note : New conversation entity which is getting created below is still required for logging purpose.

	if (rfRequestDTO == null) {
	    return null;
	} else if (rfRequestDTO.getId() != null && rfRequestDTO.getId() > 0) {
	    RfRequest rfRequest = rfRequestRepository.findOne(rfRequestDTO.getId());
	    existingConversation = rfRequest.getItem();
	}

	long startTime = System.currentTimeMillis();
	RfResponseDTO result = genericHandler.processHttpRequest(rfRequestDTO);
	long endTime = System.currentTimeMillis();
	long duration = endTime - startTime;

	conversationForLogging = ConversationConverter.convertToEntity(rfRequestDTO, result);
	conversationForLogging.setDuration(duration);

	try {
	    conversationForLogging = conversationRepository.save(conversationForLogging);
	    // Note : existingConversation will be null if the request was not saved previously.
	    if (existingConversation != null) {
		existingConversation.setRfRequest(conversationForLogging.getRfRequest());
		existingConversation.setRfResponse(conversationForLogging.getRfResponse());
		existingConversation.setDuration(duration);
	    }

	} catch (InvalidDataAccessResourceUsageException e) {
	    throw new ApiException("Please use sql as datasource, some of features are not supported by hsql", e);
	}
	ConversationDTO conversationDTO = new ConversationDTO();
	conversationDTO.setDuration(duration);
	result.setItemDTO(conversationDTO);
	return result;
    }

    /**
     * TODO : This API may not work for in-memory database (in some cases).
     */
    @RequestMapping(value = "/api/processor/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeStatusResponseDTO> runProjectById(@PathVariable("id") Long id) {
	logger.debug("Running all requests inside project : " + id);
	List<NodeStatusResponseDTO> nodeStatuses = new ArrayList<NodeStatusResponseDTO>();
	NodeStatusResponseDTO nodeStatus = null;

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
			ConversationDTO conversationDTO = rfResponseDTO.getItemDTO();

			nodeStatus = new NodeStatusResponseDTO();
			nodeStatus.setId(baseNode.getId());
			nodeStatus.setName(baseNode.getName());
			nodeStatus.setDescription(baseNode.getDescription());
			nodeStatus.setApiUrl(apiUrl);
			nodeStatus.setMethodType(methodType);
			nodeStatus.setStatusCode(rfResponseDTO.getStatus());
			nodeStatus.setDuration(conversationDTO.getDuration());
			nodeStatuses.add(nodeStatus);

			// TODO : Create ProjectRunnerLog and store nodeId as well as loggedConversationId.
		    }
		}
	    }
	}
	return nodeStatuses;
    }
}
