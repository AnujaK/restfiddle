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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.dao.RfResponseRepository;
import com.restfiddle.dao.util.ConversationConverter;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeStatusResponseDTO;
import com.restfiddle.dto.OAuth2RequestDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.User;
import com.restfiddle.exceptions.ApiException;
import com.restfiddle.handler.AssertHandler;
import com.restfiddle.handler.http.GenericHandler;
import com.restfiddle.util.EntityToDTO;

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

    @Autowired
    private RfResponseRepository rfResponseRepository;
    
    @Autowired
    private AssertHandler assertHandler; 

    @RequestMapping(value = "/api/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    ConversationDTO requestProcessor(@RequestBody RfRequestDTO rfRequestDTO) {
	Conversation existingConversation = null;
	Conversation currentConversation = null;

	// TODO : Get RfRequest Id if present as part of this request and update the existing conversation entity.
	// Note : New conversation entity which is getting created below is still required for logging purpose.

	if (rfRequestDTO == null) {
	    return null;
	} else if (rfRequestDTO.getId() != null && !rfRequestDTO.getId().isEmpty()) {
	    RfRequest rfRequest = rfRequestRepository.findOne(rfRequestDTO.getId());
	    String conversationId = rfRequest != null ? rfRequest.getConversationId() : null;
	    existingConversation = conversationId != null ? conversationRepository.findOne(conversationId) : null;
	    //finding updated existing conversation 
	    existingConversation = existingConversation != null ? nodeRepository.findOne(existingConversation.getNodeId()).getConversation() : null;
	    rfRequestDTO.setAssertionDTO(EntityToDTO.toDTO(existingConversation.getRfRequest().getAssertion()));
	}

	long startTime = System.currentTimeMillis();
	RfResponseDTO result = genericHandler.processHttpRequest(rfRequestDTO);
	long endTime = System.currentTimeMillis();
	long duration = endTime - startTime;
	
	assertHandler.runAssert(result);

	currentConversation = ConversationConverter.convertToEntity(rfRequestDTO, result);
	
	if (existingConversation != null) {
	    currentConversation.getRfRequest().setAssertion(existingConversation.getRfRequest().getAssertion());
	}

	
	rfRequestRepository.save(currentConversation.getRfRequest());
	rfResponseRepository.save(currentConversation.getRfResponse());

	currentConversation.setDuration(duration);
	
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if(principal instanceof User){
	    currentConversation.setLastModifiedBy((User) principal);
	}
	
	Date currentDate = new Date();
	currentConversation.setCreatedDate(currentDate);
	currentConversation.setLastModifiedDate(currentDate);
	try {
	    currentConversation = conversationRepository.save(currentConversation);

	    currentConversation.getRfRequest().setConversationId(currentConversation.getId());
	    rfRequestRepository.save(currentConversation.getRfRequest());

	    // Note : existingConversation will be null if the request was not saved previously.
	    if (existingConversation != null && existingConversation.getNodeId() != null) {
		BaseNode node = nodeRepository.findOne(existingConversation.getNodeId());
		currentConversation.setNodeId(node.getId());
		currentConversation.setName(node.getName());
		
		node.setConversation(currentConversation);
		node.setLastModifiedDate(currentDate);
		if(principal instanceof User){
		    currentConversation.setLastModifiedBy((User) principal);
		}
		nodeRepository.save(node);
	    }
	    
	    conversationRepository.save(currentConversation);

	} catch (InvalidDataAccessResourceUsageException e) {
	    throw new ApiException("Please use sql as datasource, some of features are not supported by hsql", e);
	}
	ConversationDTO conversationDTO = new ConversationDTO();
	conversationDTO.setDuration(duration);
	conversationDTO.setRfResponseDTO(result);
	result.setItemDTO(conversationDTO);
	return conversationDTO;
    }

    @RequestMapping(value = "/api/processor/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeStatusResponseDTO> runProjectById(@PathVariable("id") String id) {
	logger.debug("Running all requests inside project : " + id);

	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);
	List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes);
	return nodeStatuses;
    }

    @RequestMapping(value = "/api/processor/folders/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeStatusResponseDTO> runFolderById(@PathVariable("id") String id) {
	logger.debug("Running all requests inside folder : " + id);

	List<BaseNode> listOfNodes = nodeRepository.getChildren(id);
	List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes);
	return nodeStatuses;
    }

    private List<NodeStatusResponseDTO> runNodes(List<BaseNode> listOfNodes) {
	List<NodeStatusResponseDTO> nodeStatuses = new ArrayList<NodeStatusResponseDTO>();
	NodeStatusResponseDTO nodeStatus = null;
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

			RfResponseDTO rfResponseDTO = requestProcessor(rfRequestDTO).getRfResponseDTO();
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

    @RequestMapping(value = "/api/oauth/form", method = RequestMethod.POST)
    public ModelAndView oauthFormRedirect(@ModelAttribute OAuth2RequestDTO oAuth2RequestDTO) throws URISyntaxException {
	List<String> scopes = oAuth2RequestDTO.getScopes();
	String authorizationUrl = oAuth2RequestDTO.getAuthorizationUrl();
	if (authorizationUrl == null || authorizationUrl.isEmpty()) {
	    return null;
	}
	URIBuilder uriBuilder = new URIBuilder(authorizationUrl);
	List<NameValuePair> queryParams = uriBuilder.getQueryParams();
	List<String> responseTypes = new ArrayList<String>();
	if (queryParams != null && !queryParams.isEmpty()) {
	    for (NameValuePair nameValuePair : queryParams) {
		if ("response_type".equals(nameValuePair.getName())) {
		    responseTypes.add(nameValuePair.getValue());
		    break;
		}
	    }
	}

	BrowserClientRequestUrl browserClientRequestUrl = new BrowserClientRequestUrl(authorizationUrl, oAuth2RequestDTO.getClientId());
	if (!responseTypes.isEmpty()) {
	    browserClientRequestUrl = browserClientRequestUrl.setResponseTypes(responseTypes);
	}
	String url = browserClientRequestUrl.setState("restfiddle").setScopes(scopes).setRedirectUri("http://localhost:8080/oauth/response").build();

	return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/oauth/demo-redirect", method = RequestMethod.GET)
    @Deprecated
    public ModelAndView oauthRedirect() {
	List<String> scopes = new ArrayList<String>();
	scopes.add("https://www.googleapis.com/auth/userinfo.profile");
	String url = new BrowserClientRequestUrl("https://accounts.google.com/o/oauth2/auth",
		"82089573969-nocs1ulh96n5kfut1bh5cq7n1enlfoe8.apps.googleusercontent.com").setState("restfiddle").setScopes(scopes)
		.setRedirectUri("http://localhost:8080/oauth/response").build();

	return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/api/oauth/redirect", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    @Deprecated
    String oauth2Redirect(@RequestBody OAuth2RequestDTO oAuth2RequestDTO) {
	List<String> scopes = oAuth2RequestDTO.getScopes();
	String url = new BrowserClientRequestUrl(oAuth2RequestDTO.getAuthorizationUrl(), oAuth2RequestDTO.getClientId()).setState("restfiddle")
		.setScopes(scopes).setRedirectUri("http://localhost:8080/oauth/response").build();

	return url;
    }
}
