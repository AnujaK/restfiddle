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

import com.restfiddle.dao.ItemRepository;
import com.restfiddle.dto.ItemDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.entity.Item;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ItemController {
    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Resource
    private ItemRepository itemRepository;

    @RequestMapping(value = "/api/items", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Item create(@RequestBody ItemDTO itemDTO) {
	logger.debug("Creating a new item with information: " + itemDTO);

	Item item = new Item();

	item.setName(itemDTO.getName());
	item.setDescription(itemDTO.getDescription());

	RfRequest rfRequest = new RfRequest();
	rfRequest.setName("req1");
	item.setRfRequest(rfRequest);

	RfResponse rfResponse = new RfResponse();
	rfResponse.setName("res1");
	item.setRfResponse(rfResponse);

	return itemRepository.save(item);
    }

    @RequestMapping(value = "/api/items/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Item delete(@PathVariable("id") Long id) {
	logger.debug("Deleting item with id: " + id);

	Item deleted = itemRepository.findOne(id);

	itemRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/items", method = RequestMethod.GET)
    public @ResponseBody
    List<Item> findAll() {
	logger.debug("Finding all items");

	// Return 10 records for now.
	Pageable topRecords = new PageRequest(0, 10);
	Page<Item> result = itemRepository.findAll(topRecords);

	List<Item> content = result.getContent();

	// TODO : work in progress
	for (Item item : content) {
	    byte[] body = item.getRfResponse().getBody();
	    String strBody = new String(body);
	    System.out.println(strBody);
	}
	return content;
    }

    @RequestMapping(value = "/api/items/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Item findById(@PathVariable("id") Long id) {
	logger.debug("Finding item by id: " + id);

	return itemRepository.findOne(id);
    }

    @RequestMapping(value = "/api/items/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Item update(@PathVariable("id") Long id, @RequestBody ProjectDTO updated) {
	logger.debug("Updating item with information: " + updated);

	Item item = itemRepository.findOne(updated.getId());

	item.setName(updated.getName());
	item.setDescription(updated.getDescription());

	return item;
    }

}
