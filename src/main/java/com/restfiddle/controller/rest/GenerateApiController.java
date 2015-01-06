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

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.GenericEntityDataRepository;
import com.restfiddle.dao.GenericEntityRepository;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.StatusResponse;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.GenericEntityData;
import com.restfiddle.entity.GenericEntityField;

@RestController
@Transactional
public class GenerateApiController {
    Logger logger = LoggerFactory.getLogger(GenerateApiController.class);

    @Autowired
    private NodeController nodeController;

    @Autowired
    private ConversationController conversationController;

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenericEntityDataRepository genericEntityDataRepository;

    @RequestMapping(value = "/api/entities/{id}/generate-api", method = RequestMethod.POST)
    public @ResponseBody
    StatusResponse generateApiByEntityId(@PathVariable("id") Long id) {
	logger.debug("Generating APIs for entity with id: " + id);

	GenericEntity entity = genericEntityRepository.findOne(id);
	BaseNode entityNode = entity.getBaseNode();

	return generateApi(entityNode);
    }

    public StatusResponse generateApi(BaseNode entityNode) {
	GenericEntity genericEntity = entityNode.getGenericEntity();

	// API to GENERATE >> List of Entity Data
	ConversationDTO conversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/" + entityNode.getProject().getId() + "/entities/" + entityNode.getName() + "/list");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	Conversation createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());

	NodeDTO childNode = new NodeDTO();
	childNode.setName("GET List of " + entityNode.getName());
	childNode.setProjectId(entityNode.getProject().getId());
	childNode.setConversationDTO(conversationDTO);
	BaseNode createdNode = nodeController.create(entityNode.getId(), childNode);

	// API to GENERATE >> Get Entity Data By Id
	conversationDTO = new ConversationDTO();
	rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/" + entityNode.getProject().getId() + "/entities/" + entityNode.getName() + "/1");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());

	childNode = new NodeDTO();
	childNode.setName("GET " + entityNode.getName() + " by Id");
	childNode.setProjectId(entityNode.getProject().getId());
	childNode.setConversationDTO(conversationDTO);
	createdNode = nodeController.create(entityNode.getId(), childNode);

	// API to GENERATE >> Create Entity Data
	conversationDTO = new ConversationDTO();
	rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/" + entityNode.getProject().getId() + "/entities/" + entityNode.getName());
	rfRequestDTO.setMethodType("POST");

	// Create JSON template for the entity data based on fields and set it as api body.
	List<GenericEntityField> fields = genericEntity.getFields();
	JSONObject jsonObject = new JSONObject();
	for (GenericEntityField genericEntityField : fields) {
	    String type = genericEntityField.getType();
	    if ("STRING".equalsIgnoreCase(type)) {
		jsonObject.put(genericEntityField.getName(), "");
	    } else if ("LONG".equalsIgnoreCase(type)) {
		jsonObject.put(genericEntityField.getName(), Long.valueOf(1));
	    } else if ("INTEGER".equalsIgnoreCase(type)) {
		jsonObject.put(genericEntityField.getName(), Integer.valueOf(1));
	    } else if ("DATE".equalsIgnoreCase(type)) {
		jsonObject.put(genericEntityField.getName(), new Date());
	    }

	}
	// Make a pretty-printed JSON text of this JSONObject.
	rfRequestDTO.setApiBody(jsonObject.toString(4));
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());

	childNode = new NodeDTO();
	childNode.setName("Create " + entityNode.getName());
	childNode.setProjectId(entityNode.getProject().getId());
	childNode.setConversationDTO(conversationDTO);
	createdNode = nodeController.create(entityNode.getId(), childNode);

	return null;
    }

    @RequestMapping(value = "/api/{projectId}/entities/{name}/list", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    StatusResponse getEntityDataList(@PathVariable("projectId") Long projectId, @PathVariable("name") String entityName) {
	//
	return new StatusResponse();
    }

    @RequestMapping(value = "/api/{projectId}/entities/{name}/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntityData getEntityDataById(@PathVariable("projectId") Long projectId, @PathVariable("name") String entityName,
	    @PathVariable("id") String entityDataId) {
	return genericEntityDataRepository.findOne(entityDataId);
    }

    /**
     * [NOTE] http://stackoverflow.com/questions/25953056/how-to-access-fields-of-converted-json-object-sent-in-post-body
     */
    @RequestMapping(value = "/api/{projectId}/entities/{name}", method = RequestMethod.POST, headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody
    GenericEntityData createEntityData(@PathVariable("projectId") Long projectId, @PathVariable("name") String entityName,
	    @RequestBody Object genericEntityDataDTO) {
	GenericEntityData entityData = new GenericEntityData();

	entityData.setData(genericEntityDataDTO.toString());
	// Get entity by name and set here.
	// entityData.setGenericEntity(genericEntity);

	return genericEntityDataRepository.save(entityData);
    }
}
