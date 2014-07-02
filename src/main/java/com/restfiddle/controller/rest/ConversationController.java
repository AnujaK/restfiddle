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

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ConversationController {
    Logger logger = LoggerFactory.getLogger(ConversationController.class);

    @Resource
    private ConversationRepository itemRepository;

    @RequestMapping(value = "/api/conversations", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Conversation create(@RequestBody ConversationDTO conversationDTO) {
	logger.debug("Creating a new item with information: " + conversationDTO);

	Conversation conversation = new Conversation();
	conversation.setName(conversationDTO.getName());
	conversation.setDescription(conversationDTO.getDescription());

	RfRequestDTO rfRequestDTO = conversationDTO.getRfRequestDTO();
	RfRequest rfRequest = new RfRequest();
	if(null != rfRequestDTO){
		rfRequest.setApiUrl(rfRequestDTO.getApiUrl());
		rfRequest.setApiBody(rfRequestDTO.getApiBody());
		rfRequest.setMethodType(rfRequestDTO.getMethodType());
	}
	conversation.setRfRequest(rfRequest);
	RfResponse rfResponse = new RfResponse();
	rfResponse.setName("res1");
	conversation.setRfResponse(rfResponse);

	return itemRepository.save(conversation);
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Conversation delete(@PathVariable("conversationId") Long conversationId) {
	logger.debug("Deleting item with id: " + conversationId);

	Conversation deleted = itemRepository.findOne(conversationId);

	itemRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/conversations", method = RequestMethod.GET)
    public @ResponseBody
    List<Conversation> findAll() {
	logger.debug("Finding all items");

	// Return 10 records for now.
	Pageable topRecords = new PageRequest(0, 10);
	Page<Conversation> result = itemRepository.findAll(topRecords);

	List<Conversation> content = result.getContent();

	// TODO : work in progress
	for (Conversation item : content) {
	    byte[] body = item.getRfResponse().getBody();
	    if (body != null) {
		String strBody = new String(body);
		System.out.println(strBody);
	    }
	}
	return content;
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.GET)
    public @ResponseBody
    Conversation findById(@PathVariable("conversationId") Long conversationId) {
	logger.debug("Finding item by id: " + conversationId);

	return itemRepository.findOne(conversationId);
    }

    @RequestMapping(value = "/api/conversations/{conversationId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Conversation update(@PathVariable("conversationId") Long conversationId, @RequestBody ConversationDTO updated) {
	logger.debug("Updating item with information: " + updated);

	Conversation item = itemRepository.findOne(updated.getId());

	item.setName(updated.getName());
	item.setDescription(updated.getDescription());

	RfRequestDTO rfRequestDTO = updated.getRfRequestDTO();
	RfRequest rfRequest = item.getRfRequest();
	if(null != rfRequestDTO){
		rfRequest.setApiUrl(rfRequestDTO.getApiUrl());
		rfRequest.setApiBody(rfRequestDTO.getApiBody());
		rfRequest.setMethodType(rfRequestDTO.getMethodType());
	}
	item.setRfRequest(rfRequest);
	return item;
    }

}
