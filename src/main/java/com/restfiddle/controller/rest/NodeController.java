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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.GenericEntityRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.ProjectRepository;
import com.restfiddle.dao.TagRepository;
import com.restfiddle.dao.util.TreeNodeBuilder;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.Tag;
import com.restfiddle.util.EntityToDTO;
import com.restfiddle.util.TreeNode;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class NodeController {
    Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenerateApiController generateApiController;

    // Note : Creating a node requires parentId. Project-node is the root node and it is created during project creation.
    @RequestMapping(value = "/api/nodes/{parentId}/children", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    NodeDTO create(@PathVariable("parentId") String parentId, @RequestBody NodeDTO nodeDTO) {
	logger.debug("Creating a new node with information: " + nodeDTO);

	BaseNode node = new BaseNode();

	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());
	node.setNodeType(nodeDTO.getNodeType());
	node.setStarred(nodeDTO.getStarred());

	node.setParentId(parentId);
	// TODO : Set the appropriate node position
	node.setPosition(0L);
	
	node = nodeRepository.save(node);

	if (nodeDTO.getConversationDTO() != null && nodeDTO.getConversationDTO().getId() != null) {
	    Conversation conversation = conversationRepository.findOne(nodeDTO.getConversationDTO().getId());
	    node.setConversation(conversation);
	    conversation.setNodeId(node.getId());
	    conversationRepository.save(conversation);
	}

	if (nodeDTO.getGenericEntityDTO() != null && nodeDTO.getGenericEntityDTO().getId() != null) {
	    GenericEntity genericEntity = genericEntityRepository.findOne(nodeDTO.getGenericEntityDTO().getId());
	    node.setGenericEntity(genericEntity);
	}
	
	Project project = projectRepository.findOne(nodeDTO.getProjectId());
	node.setProjectId(project.getId());

	BaseNode savedNode = nodeRepository.save(node);
	
	//set tags
	List<Tag> tags = new ArrayList<Tag>();
	
	List<TagDTO> tagDTOs = nodeDTO.getTags();
	if (tagDTOs != null && !tagDTOs.isEmpty()) {
	    List<String> tagIds = new ArrayList<String>();
	    for (TagDTO tagDTO : tagDTOs) {
	    	tagIds.add(tagDTO.getId());
	    }
	    tags = (List<Tag>) tagRepository.findAll(tagIds);
	}
	savedNode.setTags(tags);
	savedNode = nodeRepository.save(savedNode);
	
	// Generate APIs for Entity
	if (nodeDTO.getGenericEntityDTO() != null && nodeDTO.getGenericEntityDTO().getId() != null) {
	    generateApiController.generateApi(savedNode);
	}
	return EntityToDTO.toDTO(savedNode);
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    void delete(@PathVariable("id") String id) {
	logger.debug("Deleting node with id: " + id);

	BaseNode nodeToDelete = nodeRepository.findOne(id);
	deleteNodeRecursively(nodeToDelete);

	// return deleted;
    }

    private void deleteNodeRecursively(BaseNode node) {
	String nodeType = node.getNodeType();
	if (nodeType != null
		&& (NodeType.FOLDER.name().equalsIgnoreCase(nodeType) || NodeType.PROJECT.name().equalsIgnoreCase(nodeType) || NodeType.ENTITY.name()
			.equalsIgnoreCase(nodeType))) {
	    List<BaseNode> children = getChildren(node.getId());
	    if (children != null && !children.isEmpty()) {
		for (BaseNode childNode : children) {
		    deleteNodeRecursively(childNode);
		}
	    }
	}
	// This is just a workaround added for now.
	if (nodeType != null && NodeType.FOLDER.name().equalsIgnoreCase(nodeType)) {
	    if (node.getGenericEntity() != null) {
		genericEntityRepository.delete(node.getGenericEntity());
	    }

	} else if (nodeType != null && NodeType.ENTITY.name().equalsIgnoreCase(nodeType)) {
	    genericEntityRepository.delete(node.getGenericEntity());
	}
	nodeRepository.delete(node);
    }

    @RequestMapping(value = "/api/nodes", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> findAll() {
	logger.debug("Finding all nodes");

	return nodeRepository.findAll();
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.GET)
    public @ResponseBody
    BaseNode findById(@PathVariable("id") String id) {
	logger.debug("Finding node by id: " + id);

	BaseNode baseNode = nodeRepository.findOne(id);
	baseNode.getConversation();

	return baseNode;
    }

    @RequestMapping(value = "/api/nodes/{parentId}/children", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> getChildren(@PathVariable("parentId") String parentId) {
	logger.debug("Finding children nodes");

	return nodeRepository.getChildren(parentId);
    }

    @RequestMapping(value = "/api/nodes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    BaseNode update(@PathVariable("id") String id, @RequestBody NodeDTO updated) {
	logger.debug("Updating node with information: " + updated);

	BaseNode node = nodeRepository.findOne(updated.getId());

	if (updated.getName() != null) {
	    node.setName(updated.getName());
	}

	if (updated.getDescription() != null) {
	    node.setDescription(updated.getDescription());
	}

	if (updated.getStarred() != null) {
	    node.setStarred(updated.getStarred());
	}

	nodeRepository.save(node);

	return node;
    }

    // Get tree-structure for a project. Id parameter is the project-reference node-id.
    @RequestMapping(value = "/api/nodes/{id}/tree", method = RequestMethod.GET)
    public @ResponseBody
    TreeNode getProjectTree(@PathVariable("id") String id) {
	// Note : There must be a better way of doing it. This method is written in a hurry.

	// Get project Id from the reference node
	BaseNode projectRefNode = nodeRepository.findOne(id);

	String projectId = projectRefNode.getProjectId();

	// Get the list of nodes for a project.
	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(projectId);

	// Creating a map of nodes with node-id as key
	Map<String, BaseNode> baseNodeMap = new HashMap<String, BaseNode>();
	Map<String, TreeNode> treeNodeMap = new HashMap<String, TreeNode>();
	TreeNode rootNode = null;
	TreeNode treeNode = null;
	TreeNode parentTreeNode = null;
	String methodType = "";

	for (BaseNode baseNode : listOfNodes) {
	    String nodeId = baseNode.getId();
	    baseNodeMap.put(nodeId, baseNode);

	    if (baseNode.getConversation() != null) {
		methodType = baseNode.getConversation().getRfRequest().getMethodType();
	    }
	    treeNode = TreeNodeBuilder.createTreeNode(nodeId, baseNode.getName(), baseNode.getDescription(), baseNode.getNodeType(), baseNode.getStarred(), methodType);
	    treeNodeMap.put(nodeId, treeNode);
	}

	for (BaseNode baseNode : listOfNodes) {
	    String nodeId = baseNode.getId();
	    String parentId = baseNode.getParentId();

	    treeNode = treeNodeMap.get(nodeId);

	    if (NodeType.PROJECT.name().equals(baseNode.getNodeType())) {
		// Identify root node for a project
		rootNode = treeNode;
	    } else {
		// Build parent node
		parentTreeNode = treeNodeMap.get(parentId);

		// Set parent tree node
		treeNode.setParent(parentTreeNode);

		// Add child node to the parent
		parentTreeNode.getChildren().add(treeNode);
	    }
	}
	return rootNode;
    }

    @RequestMapping(value = "/api/nodes/starred", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeDTO> findStarredNodes(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "limit", required = false) Integer limit) {
	logger.debug("Finding starred nodes.");

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

	Page<BaseNode> paginatedStarredNodes = nodeRepository.findStarredNodes(pageable);
	
	List<BaseNode> starredNodes = paginatedStarredNodes.getContent();
	long totalElements = paginatedStarredNodes.getTotalElements();
	
	List<NodeDTO> response = new ArrayList<NodeDTO>();
	for(BaseNode item : starredNodes){
	    response.add(EntityToDTO.toDTO(item));
	}
	
	System.out.println("totalElements : "+totalElements);
	return response;
    }

    @RequestMapping(value = "/api/nodes/{id}/tags", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Boolean addTags(@PathVariable("id") String id, @RequestBody List<TagDTO> tagDTOs) {
	logger.debug("Adding the following tags: " + tagDTOs);

	BaseNode node = nodeRepository.findOne(id);

	List<Tag> tags = new ArrayList<Tag>();
	if (tagDTOs != null && !tagDTOs.isEmpty()) {
	    List<String> tagIds = new ArrayList<String>();
	    for (TagDTO tagDTO : tagDTOs) {
		tagIds.add(tagDTO.getId());
	    }
	    tags = (List<Tag>) tagRepository.findAll(tagIds);
	}
	node.setTags(tags);

	nodeRepository.save(node);
	return Boolean.TRUE;
    }
}