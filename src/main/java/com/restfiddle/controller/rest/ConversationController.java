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
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.dao.RfResponseRepository;
import com.restfiddle.dao.util.ConversationConverter;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.PaginatedResponse;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;
import com.restfiddle.util.EntityToDTO;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ConversationController {
    Logger logger = LoggerFactory.getLogger(ConversationController.class);

    @Resource
    private ConversationRepository itemRepository;

    @Autowired
    private RfRequestRepository rfRequestRepository;

    @Autowired
    private RfResponseRepository rfResponseRepository;

    @RequestMapping(value = "/api/conversations", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ConversationDTO create(@RequestBody ConversationDTO conversationDTO) {
	logger.debug("Creating a new item with information: " + conversationDTO);

	RfRequestDTO rfRequestDTO = conversationDTO.getRfRequestDTO();
	RfResponseDTO rfResponseDTO = new RfResponseDTO();

	Conversation conversation = ConversationConverter.convertToEntity(rfRequestDTO, rfResponseDTO);
	conversation.setName(conversationDTO.getName());
	conversation.setDescription(conversationDTO.getDescription());
	conversation.setCreatedDate(new Date());
	conversation.setLastModifiedDate(new Date());
	if(conversationDTO.getNodeDTO() != null )
	    conversation.setNodeId(conversationDTO.getNodeDTO().getId());

	rfRequestRepository.save(conversation.getRfRequest());
	rfResponseRepository.save(conversation.getRfResponse());

	conversation = itemRepository.save(conversation);

	conversation.getRfRequest().setConversationId(conversation.getId());
	rfRequestRepository.save(conversation.getRfRequest());

	return EntityToDTO.toDTO(conversation);
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Conversation delete(@PathVariable("conversationId") String conversationId) {
	logger.debug("Deleting item with id: " + conversationId);

	Conversation deleted = itemRepository.findOne(conversationId);

	itemRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/conversations", method = RequestMethod.GET)
    public @ResponseBody
    PaginatedResponse<ConversationDTO> findAll(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "limit", required = false) Integer limit) {
	logger.debug("Finding all items");

	int pageNo = 0;
	if (page != null && page > 0) {
	    pageNo = page;
	}

	int numberOfRecords = 10;
	if (limit != null && limit > 0) {
	    numberOfRecords = limit;
	}

	Sort sort = new Sort(Direction.DESC, "lastModifiedDate");
	Pageable topRecords = new PageRequest(pageNo, numberOfRecords, sort);
	Page<Conversation> result = itemRepository.findAll(topRecords);

	List<Conversation> content = result.getContent();
	
	List<ConversationDTO> responseContent = new ArrayList<ConversationDTO>();
	for(Conversation item : content){
	    responseContent.add(EntityToDTO.toDTO(item));
	}

	PaginatedResponse<ConversationDTO> response = new PaginatedResponse<ConversationDTO>();
	response.setData(responseContent);
	response.setLimit(numberOfRecords);
	response.setPage(pageNo);
	response.setTotalElements(result.getTotalElements());
	response.setTotalPages(result.getTotalPages());

	for (Conversation item : content) {
	    RfRequest rfRequest = item.getRfRequest();
	    logger.debug(rfRequest.getApiUrl());
	}
	return response;
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.GET)
    public @ResponseBody
    Conversation findById(@PathVariable("conversationId") String conversationId) {
	logger.debug("Finding item by id: " + conversationId);

	return itemRepository.findOne(conversationId);
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Conversation update(@PathVariable("conversationId") String conversationId, @RequestBody ConversationDTO conversationDTO) {
	Conversation dbConversation = itemRepository.findOne(conversationDTO.getId());
	
	RfRequestDTO rfRequestDTO = conversationDTO.getRfRequestDTO();
	RfResponseDTO rfResponseDTO = new RfResponseDTO();

	Conversation conversation = ConversationConverter.convertToEntity(rfRequestDTO, rfResponseDTO);
	conversation.setId(dbConversation.getId());
	
	conversation.setName(conversationDTO.getName());
	conversation.setDescription(conversationDTO.getDescription());
	conversation.setCreatedDate(new Date());
	conversation.setLastModifiedDate(new Date());
	if(conversationDTO.getNodeDTO() != null )
	    conversation.setNodeId(conversationDTO.getNodeDTO().getId());

	RfRequest rfRequest = conversation.getRfRequest();
	RfRequest dbRfRequest = dbConversation.getRfRequest();
	if (dbRfRequest != null) {
	    rfRequest.setId(dbRfRequest.getId());
	}
	rfRequest.setConversationId(conversation.getId());
	rfRequestRepository.save(rfRequest);
	
	RfResponse rfResponse = conversation.getRfResponse();
	RfResponse dbRfResponse = dbConversation.getRfResponse();
	if(dbRfResponse != null){
	    rfResponse.setId(dbRfResponse.getId());
	}
	rfResponseRepository.save(rfResponse);

	conversation = itemRepository.save(conversation);

	return conversation;
    }
}
