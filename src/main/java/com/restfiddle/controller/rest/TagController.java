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
 * See the License for the specific language governing tags and
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
import com.restfiddle.dao.TagRepository;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Tag;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class TagController {
    Logger logger = LoggerFactory.getLogger(TagController.class);

    @Resource
    private TagRepository tagRepository;

    @Resource
    private NodeRepository nodeRepository;
    
    @RequestMapping(value = "/api/tags", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Tag create(@RequestBody TagDTO tagDTO) {
	logger.debug("Creating a new tag with information: " + tagDTO);

	Tag tag = new Tag();
	tag.setName(tagDTO.getName());
	tag.setDescription(tagDTO.getDescription());
	return tagRepository.save(tag);
    }

    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Tag delete(@PathVariable("id") Long id) {
	logger.debug("Deleting tag with id: " + id);

	Tag deleted = tagRepository.findOne(id);

	tagRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value = "/api/tags", method = RequestMethod.GET)
    public @ResponseBody
    List<Tag> findAll() {
	logger.debug("Finding all tags");

	return tagRepository.findAll();
    }

    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Tag findById(@PathVariable("id") Long id) {
	logger.debug("Finding tag by id: " + id);

	return tagRepository.findOne(id);
    }

    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Tag update(@PathVariable("id") Long id, @RequestBody TagDTO updated) {
	logger.debug("Updating tag with information: " + updated);

	Tag tag = tagRepository.findOne(updated.getId());

	tag.setName(updated.getName());
	tag.setDescription(updated.getDescription());

	return tag;
    }
    
    @RequestMapping(value = "/api/workspaces/{workspaceId}/tags/{tagId}/nodes", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> findNodesByTag(@PathVariable("workspaceId") Long workspaceId, @PathVariable("tagId") Long tagId) {
	logger.debug("Finding nodes by tag id: " + tagId);
	
	List<BaseNode> taggedNodes = nodeRepository.findTaggedNodes(tagId);
	
	return taggedNodes;
    }
    
}
