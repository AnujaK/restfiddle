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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.restfiddle.dao.ConversationRepository;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.ProjectRepository;
import com.restfiddle.dao.util.NodeTypes;
import com.restfiddle.dao.util.TreeNodeBuilder;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.TreeNode;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class NodeController {
    Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private NodeRepository nodeRepository;

    @Resource
    private ConversationRepository conversationRepository;

    // Note : Creating a node requires parentId. Project-node is the root node and it is created during project creation.
    @RequestMapping(value = "/api/nodes/{parentId}/children", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    BaseNode create(@PathVariable("parentId") Long parentId, @RequestBody NodeDTO nodeDTO) {
	logger.debug("Creating a new node with information: " + nodeDTO);

	BaseNode node = new BaseNode();

	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());
	node.setNodeType(nodeDTO.getNodeType());
	node.setStarred(nodeDTO.getStarred());

	node.setParentId(parentId);
	// TODO : Set the appropriate node position
	node.setPosition(0L);

	if (nodeDTO.getConversationDTO() != null && nodeDTO.getConversationDTO().getId() != null) {
	    Conversation conversation = conversationRepository.findOne(nodeDTO.getConversationDTO().getId());
	    node.setConversation(conversation);
	}

	Project project = projectRepository.findOne(nodeDTO.getProjectId());
	node.setProject(project);

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

	BaseNode baseNode = nodeRepository.findOne(id);
	baseNode.getConversation();
	
	return baseNode;
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

    // Get tree-structure for a project. Id parameter is the project-reference node-id.
    @RequestMapping(value = "/api/nodes/{id}/tree", method = RequestMethod.GET)
    public @ResponseBody
    TreeNode getProjectTree(@PathVariable("id") Long id) {
	// Note : There must be a better way of doing it. This method is written in a hurry.

	// Get project Id from the reference node
	BaseNode projectRefNode = nodeRepository.getOne(id);
	Long projectId = projectRefNode.getProject().getId();

	// Get the list of nodes for a project.
	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(projectId);

	// Creating a map of nodes with node-id as key
	Map<Long, BaseNode> nodeIdMap = new HashMap<Long, BaseNode>();
	for (BaseNode baseNode : listOfNodes) {
	    nodeIdMap.put(baseNode.getId(), baseNode);
	}

	TreeNode rootNode = null;
	TreeNode treeNode = null;
	TreeNode parentTreeNode = null;
	Map<Long, TreeNode> treeNodeMap = new HashMap<Long, TreeNode>();

	for (BaseNode baseNode : listOfNodes) {
	    Long nodeId = baseNode.getId();
	    Long parentId = baseNode.getParentId();

	    treeNode = TreeNodeBuilder.createTreeNode(nodeId, baseNode.getName(), baseNode.getNodeType(), baseNode.getStarred());
	    treeNodeMap.put(nodeId, treeNode);

	    if (NodeTypes.PROJECT.name().equals(baseNode.getNodeType())) {
		// Identify root node for a project
		rootNode = treeNode;
	    } else {
		// Build parent node
		BaseNode baseParentNode = nodeIdMap.get(parentId);
		parentTreeNode = treeNodeMap.get(parentId);
		if (parentTreeNode == null) {
		    parentTreeNode = TreeNodeBuilder.createTreeNode(parentId, baseParentNode.getName(), baseParentNode.getNodeType(),
			    baseParentNode.getStarred());
		}

		// Set parent tree node
		treeNode.setParent(parentTreeNode);

		// Add child node to the parent
		parentTreeNode.getChildren().add(treeNode);
	    }
	}
	return rootNode;
    }
}
