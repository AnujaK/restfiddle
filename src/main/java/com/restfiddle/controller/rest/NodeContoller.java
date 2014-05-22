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

import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.entity.BaseNode;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class NodeContoller {
    Logger logger = LoggerFactory.getLogger(NodeContoller.class);

    @Resource
    private NodeRepository nodeRepository;

    // Note : Creating a node requires parentId. Project-node is the root node and it is created during project creation.
    @RequestMapping(value = "/api/nodes/{parentId}/children", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    BaseNode create(@PathVariable("parentId") Long parentId, @RequestBody NodeDTO nodeDTO) {
	logger.debug("Creating a new node with information: " + nodeDTO);

	BaseNode node = new BaseNode();

	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());

	node.setParentId(parentId);

	return nodeRepository.save(node);
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    BaseNode delete(@PathVariable("id") Long id) {
	logger.debug("Deleting node with id: " + id);

	BaseNode deleted = nodeRepository.findOne(id);

	nodeRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/nodes", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> findAll() {
	logger.debug("Finding all nodes");

	return nodeRepository.findAll();
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.GET)
    public @ResponseBody
    BaseNode findById(@PathVariable("id") Long id) {
	logger.debug("Finding node by id: " + id);

	return nodeRepository.findOne(id);
    }

    @RequestMapping(value = "/api/nodes/{parentId}/children", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> getChildren(@PathVariable("parentId") Long parentId) {
	logger.debug("Finding children nodes");

	return nodeRepository.getChildren(parentId);
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    BaseNode update(@PathVariable("id") Long id, @RequestBody NodeDTO updated) {
	logger.debug("Updating node with information: " + updated);

	BaseNode node = nodeRepository.findOne(updated.getId());

	node.setName(updated.getName());
	node.setDescription(updated.getDescription());

	return node;
    }

}
