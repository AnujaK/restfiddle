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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ItemListRepository;
import com.restfiddle.dto.ItemListDTO;
import com.restfiddle.entity.ItemList;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ItemListController {
    Logger logger = LoggerFactory.getLogger(ItemListController.class);

    @Resource
    private ItemListRepository itemListRepository;

    @RequestMapping(value = "/api/itemlists", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ItemList create(@RequestBody ItemListDTO itemListDTO) {
	logger.debug("Creating a new itemList with information: " + itemListDTO);

	ItemList itemList = new ItemList();

	itemList.setName(itemListDTO.getName());
	itemList.setDescription(itemListDTO.getDescription());

	return itemListRepository.save(itemList);
    }

    @RequestMapping(value = "/api/itemlists/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    ItemList delete(@PathVariable("id") Long id) {
	logger.debug("Deleting itemList with id: " + id);

	ItemList deleted = itemListRepository.findOne(id);

	itemListRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/itemlists", method = RequestMethod.GET)
    public @ResponseBody
    List<ItemList> findAll() {
	logger.debug("Finding all itemlists");

	return itemListRepository.findAll();
    }

    @RequestMapping(value = "/api/itemlists/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ItemList findById(@PathVariable("id") Long id) {
	logger.debug("Finding itemList by id: " + id);

	return itemListRepository.findOne(id);
    }

    @RequestMapping(value = "/api/itemlists/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    ItemList update(@PathVariable("id") Long id, @RequestBody ItemListDTO updated) {
	logger.debug("Updating itemList with information: " + updated);

	ItemList itemList = itemListRepository.findOne(updated.getId());

	itemList.setName(updated.getName());
	itemList.setDescription(updated.getDescription());

	return itemList;
    }
}
