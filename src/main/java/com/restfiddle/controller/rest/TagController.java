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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.TagRepository;
import com.restfiddle.dao.WorkspaceRepository;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Tag;
import com.restfiddle.entity.Workspace;
import com.restfiddle.util.EntityToDTO;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class TagController {
    Logger logger = LoggerFactory.getLogger(TagController.class);

    @Resource
    private WorkspaceRepository workspaceRepository;
    
    @Resource
    private TagRepository tagRepository;

    @Resource
    private NodeRepository nodeRepository;

    @RequestMapping(value = "/api/workspaces/{workspaceId}/tags", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Tag create(@PathVariable("workspaceId") String workspaceId, @RequestBody TagDTO tagDTO) {
	logger.debug("Creating a new tag with information: " + tagDTO);

	Tag tag = new Tag();
	tag.setName(tagDTO.getName());
	tag.setDescription(tagDTO.getDescription());
	Tag savedTag = tagRepository.save(tag);
	
	// Update workspace
	Workspace workspace = workspaceRepository.findOne(workspaceId);
	workspace.getTags().add(savedTag);
	workspaceRepository.save(workspace);
	
	return savedTag;
    }
    
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
    Tag delete(@PathVariable("id") String id) {
	logger.debug("Deleting tag with id: " + id);

	Tag deleted = tagRepository.findOne(id);

	tagRepository.delete(deleted);
	List<Workspace> workspaces = workspaceRepository.findByTags(id);
	//The list has to contain exactly 1 workspace. if is just an extra check
	if (workspaces.size()>0){
	    Workspace workspace = workspaces.get(0);
	    workspace.getTags().remove(id);
	    workspaceRepository.save(workspace);
	}
	return deleted;
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/tags", method = RequestMethod.GET)
    public @ResponseBody
    List<Tag> findTagsFromAWorkspace(@PathVariable("workspaceId") String workspaceId) {
	logger.debug("Finding all tags from workspace with id " + workspaceId);

	// TODO: Reverse mapping is required for this
	// return tagRepository.findTagsFromAWorkspace(workspaceId);
	
	Workspace workspace = workspaceRepository.findOne(workspaceId);
	return workspace == null ? null : workspace.getTags();
	
    }
    
    @RequestMapping(value = "/api/tags", method = RequestMethod.GET)
    public @ResponseBody
    List<Tag> findAll() {
	logger.debug("Finding all tags");

	return tagRepository.findAll();
    }

    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Tag findById(@PathVariable("id") String id) {
	logger.debug("Finding tag by id: " + id);

	return tagRepository.findOne(id);
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/tags/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Tag update(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id, @RequestBody TagDTO updated) {
	logger.debug("Updating tag with information: " + updated);

	Tag tag = tagRepository.findOne(updated.getId());

	tag.setName(updated.getName());
	tag.setDescription(updated.getDescription());

	tagRepository.save(tag);
	return tag;
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/tags/{tagId}/nodes", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeDTO> findNodesByTag(@PathVariable("workspaceId") String workspaceId, @PathVariable("tagId") String tagId,
	    @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit) {
	logger.debug("Finding nodes by tag id: " + tagId);
	
	int pageNo = 0;
	if (page != null && page > 0) {
	    pageNo = page;
	}

	int numberOfRecords = 10;
	if (limit != null && limit > 0) {
	    numberOfRecords = limit;
	}
	Sort sort = new Sort(Direction.DESC, "lastModifiedDate");
	Pageable pageable = new PageRequest(pageNo, numberOfRecords, sort);
	
	Page<BaseNode> paginatedTaggedNodes = nodeRepository.findTaggedNodes(tagId, pageable);
	
	List<BaseNode> taggedNodes = paginatedTaggedNodes.getContent();
	
	List<NodeDTO> response = new ArrayList<NodeDTO>();
	for(BaseNode item : taggedNodes){
	    response.add(EntityToDTO.toDTO(item));
	}

	return response;
    }

}
