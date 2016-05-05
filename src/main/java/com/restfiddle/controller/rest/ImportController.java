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

import io.swagger.models.HttpMethod;
import io.swagger.models.Info;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Project;
import com.restfiddle.util.TreeNode;

@RestController
@Transactional
public class ImportController {
    Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private NodeController nodeController;

    @Autowired
    private ConversationController conversationController;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private NodeRepository nodeRepository;

    @RequestMapping(value = "/api/import/restfiddle", method = RequestMethod.POST)
    public @ResponseBody
    void importRestFiddle(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
	if (!file.isEmpty()) {
	    try {
		byte[] bytes = file.getBytes();
		ObjectMapper mapper = new ObjectMapper();
		TreeNode projectNode = mapper.readValue(bytes, TreeNode.class);
		System.out.println("projectNode : " + projectNode);
		createNodeRecursively(projectNode, projectNode.getChildren());
	    } catch (Exception e) {
		logger.error(e.getMessage(), e);
	    }
	}
    }

    private void createNodeRecursively(BaseNode parent, List<TreeNode> nodes) {
	BaseNode node;
	for (TreeNode treeNode : nodes) {
	    node = new BaseNode();
	    node.setName(treeNode.getName());
	    node.setDescription(treeNode.getDescription());
	    node.setNodeType(treeNode.getNodeType());
	    node.setStarred(treeNode.getStarred());
	    node.setPosition(treeNode.getPosition());
	    node.setProjectId(treeNode.getProjectId());
	    node.setParentId(parent.getId());// Important
	    node.setWorkspaceId(parent.getWorkspaceId());
	    node = nodeRepository.save(node);
	    List<TreeNode> children = treeNode.getChildren();
	    if (children != null && !children.isEmpty()) {
		createNodeRecursively(node, children);
	    }
	}
    }

    @RequestMapping(value = "/api/import/swagger", method = RequestMethod.POST)
    public @ResponseBody
    void importSwagger(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
	try {
	    swaggerToRFConverter(projectId, name, file);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/api/import/raml", method = RequestMethod.POST)
    public @ResponseBody
    void importRaml(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
	try {
	    ramlToRFConverter(projectId, name, file);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/api/import/postman", method = RequestMethod.POST)
    public @ResponseBody
    void importPostman(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

	if (!file.isEmpty()) {
	    try {
		byte[] bytes = file.getBytes();
		String fileContent = new String(bytes);
		JSONObject pmCollection = new JSONObject(fileContent);

		String folderName = pmCollection.getString("name");
		System.out.println(folderName);
		//
		NodeDTO folderNode = createFolder(projectId, folderName);
		ConversationDTO conversationDTO;

		JSONArray requests = pmCollection.getJSONArray("requests");
		int len = requests.length();
		for (int i = 0; i < len; i++) {
		    JSONObject request = (JSONObject) requests.get(i);
		    String requestName = request.getString("name");
		    String requestDescription = request.getString("description");
		    String requestUrl = request.getString("url");
		    String requestMethod = request.getString("method");

		    conversationDTO = new ConversationDTO();

		    // TODO : Set workspace Id to the conversation
		    // conversationDTO.setWorkspaceId(project.getWorkspace().getId());

		    RfRequestDTO rfRequestDTO = new RfRequestDTO();
		    rfRequestDTO.setApiUrl(requestUrl);
		    rfRequestDTO.setMethodType(requestMethod);

		    String headersString = request.getString("headers");
		    if (headersString != null && !headersString.isEmpty()) {
			List<RfHeaderDTO> headerDTOs = new ArrayList<RfHeaderDTO>();
			RfHeaderDTO headerDTO;
			String[] headersArr = headersString.split("\n");
			for (String header : headersArr) {
			    String[] headerToken = header.split(":");
			    String headerName = headerToken[0];
			    String headerValue = headerToken[1].trim();

			    headerDTO = new RfHeaderDTO();
			    headerDTO.setHeaderName(headerName);
			    headerDTO.setHeaderValue(headerValue);
			    headerDTOs.add(headerDTO);
			}
			rfRequestDTO.setHeaders(headerDTOs);
		    }

		    String dataMode = request.getString("dataMode");
		    if ("raw".equals(dataMode)) {
			String rawModeData = request.getString("rawModeData");
			rfRequestDTO.setApiBody(rawModeData);
		    } else if ("params".equals(dataMode)) {
			JSONArray formParamsArr = request.getJSONArray("data");
			int arrLen = formParamsArr.length();

			FormDataDTO formParam;
			List<FormDataDTO> formParams = new ArrayList<FormDataDTO>();
			for (int j = 0; j < arrLen; j++) {
			    JSONObject formParamJSON = (JSONObject) formParamsArr.get(j);
			    formParam = new FormDataDTO();
			    formParam.setKey(formParamJSON.getString("key"));
			    formParam.setValue(formParamJSON.getString("value"));
			    formParams.add(formParam);
			}
			rfRequestDTO.setFormParams(formParams);
		    }

		    conversationDTO.setRfRequestDTO(rfRequestDTO);
		    ConversationDTO createdConversation = conversationController.create(conversationDTO);
		    conversationDTO.setId(createdConversation.getId());

		    // Request Node
		    NodeDTO childNode = new NodeDTO();
		    childNode.setName(requestName);
		    childNode.setDescription(requestDescription);
		    childNode.setProjectId(projectId);
		    childNode.setConversationDTO(conversationDTO);
		    NodeDTO createdChildNode = nodeController.create(folderNode.getId(), childNode);
		    System.out.println("created node : " + createdChildNode.getName());
		}
	    } catch (Exception e) {
		logger.error(e.getMessage(), e);
	    }
	}
    }

    private void ramlToRFConverter(String projectId, String name, MultipartFile file) throws IOException {
	// Raml raml = new RamlDocumentBuilder().build(ramlLocation);

    }

    private NodeDTO createFolder(String projectId, String folderName) {
	Project project = projectController.findById(null, projectId);

	NodeDTO folderNodeDTO = new NodeDTO();
	folderNodeDTO.setName(folderName);
	folderNodeDTO.setNodeType(NodeType.FOLDER.name());
	folderNodeDTO.setProjectId(projectId);

	NodeDTO collectionNode = nodeController.create(project.getProjectRef().getId(), folderNodeDTO);
	return collectionNode;
    }

    // Swagger sample json : http://petstore.swagger.io/v2/swagger.json
    private void swaggerToRFConverter(String projectId, String name, MultipartFile file) throws IOException {
	// MultipartFile file
	File tempFile = File.createTempFile("RF_SWAGGER_IMPORT", "JSON");
	file.transferTo(tempFile);
	Swagger swagger = new SwaggerParser().read(tempFile.getAbsolutePath());

	String host = swagger.getHost();
	String basePath = swagger.getBasePath();

	Info info = swagger.getInfo();
	String title = info.getTitle();
	String description = info.getDescription();

	NodeDTO folderNode = createFolder(projectId, title);
	folderNode.setDescription(description);

	ConversationDTO conversationDTO;

	Map<String, Path> paths = swagger.getPaths();
	Set<String> keySet = paths.keySet();
	for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
	    String pathKey = iterator.next();
	    Path path = paths.get(pathKey);

	    Map<HttpMethod, Operation> operationMap = path.getOperationMap();
	    Set<HttpMethod> operationsKeySet = operationMap.keySet();
	    for (Iterator<HttpMethod> operIterator = operationsKeySet.iterator(); operIterator.hasNext();) {
		HttpMethod httpMethod = operIterator.next();
		Operation operation = operationMap.get(httpMethod);

		conversationDTO = new ConversationDTO();
		RfRequestDTO rfRequestDTO = new RfRequestDTO();
		rfRequestDTO.setApiUrl("http://" + host + basePath + pathKey);
		rfRequestDTO.setMethodType(httpMethod.name());
		operation.getParameters();
		conversationDTO.setRfRequestDTO(rfRequestDTO);
		ConversationDTO createdConversation = conversationController.create(conversationDTO);
		conversationDTO.setId(createdConversation.getId());

		String operationId = operation.getOperationId();
		String summary = operation.getSummary();
		// Request Node
		NodeDTO childNode = new NodeDTO();
		childNode.setName(operationId);
		childNode.setDescription(summary);
		childNode.setProjectId(projectId);
		childNode.setConversationDTO(conversationDTO);
		NodeDTO createdChildNode = nodeController.create(folderNode.getId(), childNode);
		System.out.println("created node : " + createdChildNode.getName());
	    }
	}
    }
}
