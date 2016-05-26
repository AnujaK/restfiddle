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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.ActivityLogRepository;
import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.dao.RfResponseRepository;
import com.restfiddle.dao.util.ConversationConverter;
import com.restfiddle.dto.BodyAssertDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeStatusResponseDTO;
import com.restfiddle.dto.OAuth2RequestDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.dto.RunnerLogDTO;
import com.restfiddle.entity.ActivityLog;
import com.restfiddle.entity.BaseEntity;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.Environment;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RunnerLog;
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
    private static final String HTTP_LOCALHOST_8080_OAUTH_RESPONSE = "http://localhost:8080/oauth/response";

    private static final String RESTFIDDLE = "restfiddle";

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

    @Autowired
    private RunnerLogController runnerLogController;

    @Autowired
    private EnvironmentController environmentController;
    
    @Resource
    private ActivityLogRepository activityLogRepository;

    @RequestMapping(value = "/api/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    ConversationDTO requestProcessor(@RequestParam(value = "runnerLogId", required = false) String runnerLogId, @RequestBody RfRequestDTO rfRequestDTO) {
	Conversation existingConversation = null;
	Conversation currentConversation;

	// TODO : Get RfRequest Id if present as part of this request and update the existing conversation entity.
	// Note : New conversation entity which is getting created below is still required for logging purpose.
	
	if (rfRequestDTO == null) {
	    return null;
	} else if (rfRequestDTO.getId() != null && !rfRequestDTO.getId().isEmpty()) {
	    RfRequest rfRequest = rfRequestRepository.findOne(rfRequestDTO.getId());
	    String conversationId = rfRequest != null ? rfRequest.getConversationId() : null;
	    existingConversation = conversationId != null ? conversationRepository.findOne(conversationId) : null;
	    // finding updated existing conversation
	    existingConversation = existingConversation != null ? nodeRepository.findOne(existingConversation.getNodeId()).getConversation() : null;
	    if(existingConversation != null) {
	       rfRequestDTO.setAssertionDTO(EntityToDTO.toDTO(existingConversation.getRfRequest().getAssertion()));
	    }
	}
	
	long startTime = System.currentTimeMillis();
	RfResponseDTO result = genericHandler.processHttpRequest(rfRequestDTO);
	long endTime = System.currentTimeMillis();
	long duration = endTime - startTime;

	if (result != null) {
	    String nodeId = null;
	    if (existingConversation != null && existingConversation.getNodeId() != null) {
		nodeId = existingConversation.getNodeId();
	    }
	    assertHandler.runAssert(result, nodeId);
	}

	currentConversation = ConversationConverter.convertToEntity(rfRequestDTO, result);

	// This is used to get project-runner/folder-runner logs
	currentConversation.setRunnerLogId(runnerLogId);

	if (existingConversation != null) {
	    currentConversation.getRfRequest().setAssertion(existingConversation.getRfRequest().getAssertion());
	}

	rfRequestRepository.save(currentConversation.getRfRequest());
	rfResponseRepository.save(currentConversation.getRfResponse());

	currentConversation.setDuration(duration);

	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if (principal instanceof User) {
	    currentConversation.setLastModifiedBy((User) principal);
	}

	Date currentDate = new Date();
	currentConversation.setCreatedDate(currentDate);
	currentConversation.setLastModifiedDate(currentDate);
	currentConversation.setLastRunDate(currentDate);
	
	currentConversation.setName(currentConversation.getRfRequest().getApiUrl());
	try {
	    currentConversation = conversationRepository.save(currentConversation);

	    currentConversation.getRfRequest().setConversationId(currentConversation.getId());
	    rfRequestRepository.save(currentConversation.getRfRequest());
	    
	    ActivityLog activityLog = null;

	    // Note : existingConversation will be null if the request was not saved previously.
	    if (existingConversation != null && existingConversation.getNodeId() != null) {
		BaseNode node = nodeRepository.findOne(existingConversation.getNodeId());
		currentConversation.setNodeId(node.getId());
		currentConversation.setName(node.getName());
		
		activityLog = activityLogRepository.findActivityLogByDataId(node.getId());
	    }
	    
	    if (principal instanceof User) {
		currentConversation.setLastModifiedBy((User) principal);
	    }

	    conversationRepository.save(currentConversation);
	    
	    if (activityLog == null) {
	        activityLog = new ActivityLog();
	        if(existingConversation != null) {
	           activityLog.setDataId(existingConversation.getNodeId());
	        }
	        activityLog.setType("CONVERSATION");
	    }
	    
	    activityLog.setName(currentConversation.getName());
	    activityLog.setWorkspaceId(currentConversation.getWorkspaceId());
	    activityLog.setLastModifiedDate(currentDate);
	    
	    List<BaseEntity> logData = activityLog.getData();
	    logData.add(0, currentConversation);
	    
	    activityLogRepository.save(activityLog);

	} catch (InvalidDataAccessResourceUsageException e) {
	    throw new ApiException("Please use sql as datasource, some of features are not supported by hsql", e);
	}
	ConversationDTO conversationDTO = new ConversationDTO();
	conversationDTO.setWorkspaceId(rfRequestDTO.getWorkspaceId());
	conversationDTO.setDuration(duration);
	conversationDTO.setRfResponseDTO(result);
	if (result != null) {
	    result.setItemDTO(conversationDTO);
	}
	return conversationDTO;
    }

    @RequestMapping(value = "/api/processor/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeStatusResponseDTO> runProjectById(@PathVariable("id") String id, @RequestParam(value = "envId", required = false) String envId) {
	logger.debug("Running all requests inside project : " + id);

	RunnerLogDTO runnerLogDTO = new RunnerLogDTO();
	// TODO : Set project node id
	// runnerLogDTO.setNodeId(nodeId);

	RunnerLog log = runnerLogController.create(runnerLogDTO);

	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);
	List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes, envId, log.getId());
	return nodeStatuses;
    }

    // Handle environment passing here as above. Passing null as of now
    @RequestMapping(value = "/api/processor/folders/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeStatusResponseDTO> runFolderById(@PathVariable("id") String id, @RequestParam(value = "envId", required = false) String envId) {
	logger.debug("Running all requests inside folder : " + id);

	RunnerLogDTO runnerLogDTO = new RunnerLogDTO();
	// TODO : Set project node id
	// runnerLogDTO.setNodeId(nodeId);

	RunnerLog log = runnerLogController.create(runnerLogDTO);

	List<BaseNode> listOfNodes = nodeRepository.getChildren(id);
	List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes, envId, log.getId());
	return nodeStatuses;
    }

    private List<NodeStatusResponseDTO> runNodes(List<BaseNode> listOfNodes, String envId, String runnerLogId) {
	// TODO : Regex is hard-coded for now. User will have the option to choose different regular expressions.
	// TODO : Need to add the option to change regex in settings (UI).

	List<NodeStatusResponseDTO> nodeStatuses = new ArrayList<NodeStatusResponseDTO>();
	NodeStatusResponseDTO nodeStatus;

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
			String evaulatedApiUrl = evaluateApiUrl(envId, apiUrl);
			RfRequestDTO rfRequestDTO = new RfRequestDTO();
			rfRequestDTO.setMethodType(methodType);
			rfRequestDTO.setApiUrl(evaulatedApiUrl);
			rfRequestDTO.setApiBody(apiBody);
			rfRequestDTO.setAssertionDTO(EntityToDTO.toDTO(rfRequest.getAssertion()));

			RfResponseDTO rfResponseDTO = requestProcessor(runnerLogId, rfRequestDTO).getRfResponseDTO();

			nodeStatus = new NodeStatusResponseDTO();
			nodeStatus.setId(baseNode.getId());
			nodeStatus.setName(baseNode.getName());
			nodeStatus.setDescription(baseNode.getDescription());
			nodeStatus.setApiUrl(evaulatedApiUrl);
			nodeStatus.setMethodType(methodType);

			int successCount = 0;
			int failureCount = 0;
			if (rfResponseDTO != null) {
			    logger.debug(baseNode.getName() + " ran with status : " + rfResponseDTO.getStatus());
			    nodeStatus.setStatusCode(rfResponseDTO.getStatus());
			    nodeStatus.setDuration(rfResponseDTO.getItemDTO().getDuration());

			    // TODO: get AssertionDTO from rfResponseDTO. Get bodyAssertsDTOs and loop through the list to count no. of success
			    List<BodyAssertDTO> bodyAssertDTOs = rfResponseDTO.getAssertionDTO().getBodyAssertDTOs();
			    if (bodyAssertDTOs != null) {
				for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
				    if (bodyAssertDTO.isSuccess()) {
					successCount++;
				    } else {
					failureCount++;
				    }
				}
			    }
			}
			nodeStatus.setSuccessAsserts(successCount);
			nodeStatus.setFailureAsserts(failureCount);

			nodeStatuses.add(nodeStatus);

			// TODO : Create ProjectRunnerLog and store nodeId as well as loggedConversationId.
		    }
		}
	    }
	}
	return nodeStatuses;
    }

    private String evaluateApiUrl(String envId, String apiUrl) {
	String regex = "\\{\\{([^\\}\\}]*)\\}\\}";
	Environment env = null;
	if (envId != null && !envId.isEmpty()) {
	    env = environmentController.findById(envId);
	}
	if (env != null) {
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(apiUrl);
	    String tempUrl;
	    while (m.find()) {
		String exprVar = m.group(1);
		String propVal = env.getPropertyValueByName(exprVar);
		tempUrl = apiUrl.replaceFirst(regex, propVal);
		apiUrl = tempUrl;
	    }
	}
	return apiUrl;
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
	String url = browserClientRequestUrl.setState(RESTFIDDLE).setScopes(scopes).setRedirectUri(HTTP_LOCALHOST_8080_OAUTH_RESPONSE).build();

	return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/oauth/demo-redirect", method = RequestMethod.GET)
    @Deprecated
    public ModelAndView oauthRedirect() {
	List<String> scopes = new ArrayList<String>();
	scopes.add("https://www.googleapis.com/auth/userinfo.profile");
	String url = new BrowserClientRequestUrl("https://accounts.google.com/o/oauth2/auth",
		"82089573969-nocs1ulh96n5kfut1bh5cq7n1enlfoe8.apps.googleusercontent.com").setState(RESTFIDDLE).setScopes(scopes)
		.setRedirectUri(HTTP_LOCALHOST_8080_OAUTH_RESPONSE).build();

	return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/api/oauth/redirect", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    @Deprecated
    String oauth2Redirect(@RequestBody OAuth2RequestDTO oAuth2RequestDTO) {
	List<String> scopes = oAuth2RequestDTO.getScopes();
	String url = new BrowserClientRequestUrl(oAuth2RequestDTO.getAuthorizationUrl(), oAuth2RequestDTO.getClientId()).setState(RESTFIDDLE)
		.setScopes(scopes).setRedirectUri(HTTP_LOCALHOST_8080_OAUTH_RESPONSE).build();

	return url;
    }
}
