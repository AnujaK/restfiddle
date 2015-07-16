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

import java.util.ArrayList;
import java.util.List;

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

import com.restfiddle.constant.NodeType;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.Project;

@RestController
@Transactional
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private NodeController nodeController;

    @Autowired
    private ConversationController conversationController;

    @Autowired
    private ProjectController projectController;
    
    @RequestMapping(value = "/api/import", method = RequestMethod.POST)
    public @ResponseBody
    void upload(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

	if (!file.isEmpty()) {
	    try {
		byte[] bytes = file.getBytes();
		String fileContent = new String(bytes);
		JSONObject pmCollection = new JSONObject(fileContent);

		String collectionName = pmCollection.getString("name");
		System.out.println(collectionName);
		//
		Project project = projectController.findById(null, projectId);
		
		NodeDTO collectionNodeDTO = new NodeDTO();
		collectionNodeDTO.setName(collectionName);
		collectionNodeDTO.setNodeType(NodeType.FOLDER.name());
		collectionNodeDTO.setProjectId(projectId);
		
		NodeDTO collectionNode = nodeController.create(project.getProjectRef().getId(), collectionNodeDTO);

		JSONArray requests = pmCollection.getJSONArray("requests");
		int len = requests.length();
		for (int i = 0; i < len; i++) {
		    JSONObject request = (JSONObject) requests.get(i);
		    String requestName = request.getString("name");
		    String requestDescription = request.getString("description");
		    String requestUrl = request.getString("url");
		    String requestMethod = request.getString("method");

		    ConversationDTO conversationDTO = new ConversationDTO();
		    RfRequestDTO rfRequestDTO = new RfRequestDTO();
		    rfRequestDTO.setApiUrl(requestUrl);
		    rfRequestDTO.setMethodType(requestMethod);

		    String headersString = request.getString("headers");
		    if (headersString != null && !headersString.isEmpty()) {
			List<RfHeaderDTO> headerDTOs = new ArrayList<RfHeaderDTO>();
			RfHeaderDTO headerDTO = null;
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

			FormDataDTO formParam = null;
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
		    NodeDTO createdChildNode = nodeController.create(collectionNode.getId(), childNode);
		    System.out.println("created node : " + createdChildNode.getName());
		}
	    } catch (Exception e) {
		logger.error(e.getMessage(), e);
	    }
	}
    }
}
