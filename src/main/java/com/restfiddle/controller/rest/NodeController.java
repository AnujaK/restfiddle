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
import java.util.Collections;
import java.util.Comparator;
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
import com.restfiddle.controller.util.NodeUtil;
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
    private static final String PROJECT = "PROJECT";

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
	BaseNode parentNode = nodeRepository.findOne(parentId);
	node.setWorkspaceId(parentNode.getWorkspaceId());

	node.setParentId(parentId);

	// To find the last child node and its position
	long lastChildPosition = NodeUtil.findLastChildPosition(nodeRepository.getChildren(parentId));

	// Set the appropriate node position.
	node.setPosition(lastChildPosition + 1);

	node = nodeRepository.save(node);

	if (nodeDTO.getConversationDTO() != null && nodeDTO.getConversationDTO().getId() != null) {
	    Conversation conversation = conversationRepository.findOne(nodeDTO.getConversationDTO().getId());
	    node.setConversation(conversation);
	    conversation.setNodeId(node.getId());
	    conversationRepository.save(conversation);
	}

	if (nodeDTO.getGenericEntityDTO() != null && nodeDTO.getGenericEntityDTO().getId() != null) {
	    GenericEntity genericEntity = genericEntityRepository.findOne(nodeDTO.getGenericEntityDTO().getId());
	    genericEntity.setBaseNodeId(node.getId());
	    genericEntityRepository.save(genericEntity);
	    node.setGenericEntity(genericEntity);
	}

	Project project = projectRepository.findOne(nodeDTO.getProjectId());
	node.setProjectId(project.getId());

	BaseNode savedNode = nodeRepository.save(node);

	// set tags
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
	Long deletedNodePosition = nodeToDelete.getPosition();

	deleteNodesRecursively(nodeToDelete);

	BaseNode parent = nodeRepository.findOne(nodeToDelete.getParentId());
	List<BaseNode> children = nodeRepository.getChildren(parent.getId());
	if (children != null && !children.isEmpty()) {
	    for (BaseNode baseNode : children) {
		if (baseNode.getPosition() > deletedNodePosition) {
		    baseNode.setPosition(baseNode.getPosition() - 1);
		    nodeRepository.save(baseNode);
		}
	    }
	}
    }

    @RequestMapping(value = "/api/nodes/{id}/copy", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    void copy(@PathVariable("id") String id, @RequestBody NodeDTO nodeDTO) {
	BaseNode node = nodeRepository.findOne(id);
	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());

	copyNodesRecursively(node, node.getParentId());
    }

    public void copyNodesRecursively(BaseNode node, String parentId) {
	NodeDTO dto = EntityToDTO.toDTO(node);
	NodeDTO newNode = create(parentId, dto);

	String nodeType = node.getNodeType();
	if (nodeType != null
		&& (NodeType.FOLDER.name().equalsIgnoreCase(nodeType) || NodeType.PROJECT.name().equalsIgnoreCase(nodeType) || NodeType.ENTITY.name()
			.equalsIgnoreCase(nodeType))) {
	    List<BaseNode> children = getChildren(node.getId());
	    if (children != null && !children.isEmpty()) {
		for (BaseNode childNode : children) {
		    copyNodesRecursively(childNode, newNode.getId());
		}
	    }
	}
	// This is just a workaround added for now.
	if (nodeType != null && NodeType.FOLDER.name().equalsIgnoreCase(nodeType)) {
	    if (node.getGenericEntity() != null) {
		// TODO : genericEntityRepository.delete(node.getGenericEntity());
	    }

	} else if (nodeType != null && NodeType.ENTITY.name().equalsIgnoreCase(nodeType)) {
	    // TODO : genericEntityRepository.delete(node.getGenericEntity());
	}

    }

    public void deleteNodesRecursively(BaseNode node) {
	String nodeType = node.getNodeType();
	if (nodeType != null
		&& (NodeType.FOLDER.name().equalsIgnoreCase(nodeType) || NodeType.PROJECT.name().equalsIgnoreCase(nodeType) || NodeType.ENTITY.name()
			.equalsIgnoreCase(nodeType))) {
	    List<BaseNode> children = getChildren(node.getId());
	    if (children != null && !children.isEmpty()) {
		for (BaseNode childNode : children) {
		    deleteNodesRecursively(childNode);
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

    public TreeNode getProjectTree(String id) {
	return getProjectTree(id, null, null);
    }

    // Get tree-structure for a project. Id parameter is the project-reference node-id.
    @RequestMapping(value = "/api/nodes/{id}/tree", method = RequestMethod.GET)
    public @ResponseBody
    TreeNode getProjectTree(@PathVariable("id") String id, @RequestParam(value = "search", required = false) String search,
	    @RequestParam(value = "sort", required = false) String sort) {
	// Note : There must be a better way of doing it. This method is written in a hurry.

	// Get project Id from the reference node
	BaseNode projectRefNode = nodeRepository.findOne(id);

	String projectId = projectRefNode.getProjectId();

	// Get the list of nodes for a project.
	List<BaseNode> listOfNodes = nodeRepository.searchNodesFromAProject(projectId, search != null ? search : "");

	// Creating a map of nodes with node-id as key
	Map<String, BaseNode> baseNodeMap = new HashMap<String, BaseNode>();
	Map<String, TreeNode> treeNodeMap = new HashMap<String, TreeNode>();
	TreeNode rootNode = null;
	TreeNode treeNode;
	TreeNode parentTreeNode;
	String methodType = "";

	for (BaseNode baseNode : listOfNodes) {
	    String nodeId = baseNode.getId();
	    baseNodeMap.put(nodeId, baseNode);

	    if (baseNode.getConversation() != null) {
		methodType = baseNode.getConversation().getRfRequest().getMethodType();
	    }
	    treeNode = TreeNodeBuilder.createTreeNode(nodeId, baseNode.getName(), baseNode.getDescription(), baseNode.getWorkspaceId(), baseNode.getParentId(), baseNode.getPosition(), baseNode.getNodeType(),
		    baseNode.getStarred(), methodType, baseNode.getLastModifiedDate(), baseNode.getLastModifiedBy());
	    treeNode.setProjectId(projectId);
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

	if (search != null && !search.trim().equals("")) {
	    for (BaseNode baseNode : listOfNodes) {
		if (baseNode.getNodeType() != null && !NodeType.PROJECT.name().equals(baseNode.getNodeType())) {
		    TreeNode node = treeNodeMap.get(baseNode.getId());

		    if (node.getChildren().isEmpty()) {
			TreeNode parent = treeNodeMap.get(baseNode.getParentId());
			parent.getChildren().remove(node);
		    }
		}
	    }
	}

	int order = 1;
	if (sort != null) {
	    if (sort.trim().charAt(0) == '-') {
		order = -1;
		sort = sort.substring(1);
	    }
	    sortTree(rootNode, sort, order);
	}else{
	    sortTree(rootNode, "position", order);
	}
	
	return rootNode;
    }

    private void sortTree(TreeNode rootNode, String sort, final int order) {
	if (rootNode != null && rootNode.getChildren() != null) {

	    Comparator<TreeNode> comparator;

	    switch (sort) {
	    case "lastModified":
		comparator = new Comparator<TreeNode>() {

		    @Override
		    public int compare(TreeNode o1, TreeNode o2) {
			int val = 0;
			if (o1.getLastModifiedDate() != null && o2.getLastModifiedDate() != null) {
			    val = o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate());
			} else if (o1.getLastModifiedDate() != null) {
			    val = 1;
			} else if (o2.getLastModifiedDate() != null) {
			    val = -1;
			}

			return order * val;
		    }
		};
		break;
	    case "name":
		comparator = new Comparator<TreeNode>() {

		    @Override
		    public int compare(TreeNode o1, TreeNode o2) {

			return order * o1.getName().compareTo(o2.getName());
		    }
		};
		break;

	    default:
		comparator = new Comparator<TreeNode>() {

		    @Override
		    public int compare(TreeNode o1, TreeNode o2) {

			return order * o1.getPosition().compareTo(o2.getPosition());
		    }
		};
		break;
	    }

	    sortTreeNodes(rootNode, comparator);

	}
    }

    private void sortTreeNodes(TreeNode rootNode, Comparator<TreeNode> comparator) {
	if (rootNode != null && rootNode.getChildren() != null) {
	    List<TreeNode> childs = rootNode.getChildren();
	    for (TreeNode node : childs) {
		sortTreeNodes(node, comparator);
	    }

	    Collections.sort(childs, comparator);

	    if (!childs.isEmpty()) {
		rootNode.setLastModifiedDate(childs.get(0).getLastModifiedDate());
	    }
	}
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/nodes/starred", method = RequestMethod.GET)
    public @ResponseBody
    List<NodeDTO> findStarredNodes(@PathVariable("workspaceId") String workspaceId, @RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "search", required = false) String search,
	    @RequestParam(value = "sortBy", required = false) String sortBy) {
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
	if ("name".equals(sortBy)) {
	    sort = new Sort(Direction.ASC, "name");
	} else if ("lastRun".equals(sortBy)) {
	    sort = new Sort(Direction.DESC, "lastModifiedDate");
	} else if ("nameDesc".equals(sortBy)) {
	    sort = new Sort(Direction.DESC, "name");
	}
	Pageable pageable = new PageRequest(pageNo, numberOfRecords, sort);

	Page<BaseNode> paginatedStarredNodes = nodeRepository.findStarredNodes(workspaceId, search != null ? search : "", pageable);

	List<BaseNode> starredNodes = paginatedStarredNodes.getContent();
	long totalElements = paginatedStarredNodes.getTotalElements();

	List<NodeDTO> response = new ArrayList<NodeDTO>();
	for (BaseNode item : starredNodes) {
	    response.add(EntityToDTO.toDTO(item));
	}

	System.out.println("totalElements : " + totalElements);
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
    
    @RequestMapping(value = "/api/nodes/{id}/requests", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> getProjectRequests(@PathVariable("id") String id, @RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "search", required = false) String search,
	    @RequestParam(value = "sort", required = false) String sortBy) {
	// Note : There must be a better way of doing it. This method is written in a hurry.

	// Get project Id from the reference node
	BaseNode projectRefNode = nodeRepository.findOne(id);

	String projectId = projectRefNode.getProjectId();

	List<BaseNode> requestsNodes = nodeRepository.findRequestsFromAProject(projectId, search != null ? search : "");
	
	return requestsNodes;
    }

    @RequestMapping(value = "/api/nodes/{id}/move", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    void move(@PathVariable("id") String id, @RequestParam(value = "newRefNodeId", required = true) String newRefNodeId,
	    @RequestParam(value = "position", required = true) String position) {

	BaseNode node = nodeRepository.findOne(id);
	Long oldPosition = node.getPosition();
	BaseNode newRefNode = nodeRepository.findOne(newRefNodeId);
	BaseNode oldParentNode = nodeRepository.findOne(node.getParentId());
	
	Long newPosition;
	String newParentId;
	if(position.equals("over")){
	    newParentId = newRefNode.getId();
	    newPosition = (long) 1;
	}else if(position.equals("before")){
	    newParentId = newRefNode.getParentId();
	    newPosition = newRefNode.getPosition(); 
	}else{
	    newParentId = newRefNode.getParentId();
	    newPosition = newRefNode.getPosition()+1;
	}

	// Not allowed to save request under a request
	if (position.equals("over") && !(newRefNode.getNodeType().equalsIgnoreCase(PROJECT) || newRefNode.getNodeType().equalsIgnoreCase("FOLDER"))) {
	    return;
	}
	// Special case where -1 getting saved for non-project node
	if (!(node.getNodeType() != null && node.getNodeType().equalsIgnoreCase(PROJECT)) && newParentId.equals("-1")){
	    return;
	}
	// update new folder
	List<BaseNode> newFolderChildren;
	if(newRefNode.getNodeType() != null && (newRefNode.getNodeType().equalsIgnoreCase(PROJECT) || newRefNode.getNodeType().equalsIgnoreCase("FOLDER"))){
	    newFolderChildren = nodeRepository.getChildren(newRefNode.getId());
	}else{
	    newFolderChildren = nodeRepository.getChildren(newRefNode.getParentId());
	}

	node.setParentId(newParentId);
	node.setPosition(newPosition);
	nodeRepository.save(node);
	for (BaseNode newFolderChild : newFolderChildren) {
	    if (newFolderChild.getPosition() >= newPosition && newFolderChild.getId() != id) {
		newFolderChild.setPosition(newFolderChild.getPosition() + 1);
		nodeRepository.save(newFolderChild);
	    }
	}
	
	//If node is moved within the same folder, updating new folder is sufficient
	if(oldParentNode.getId().equals(newParentId)){
	    return;
	}
	// update old folder
	List<BaseNode> oldFolderChildren = nodeRepository.getChildren(oldParentNode.getId());
	if (oldFolderChildren != null && !oldFolderChildren.isEmpty()) {
	    for (BaseNode oldFolderChild : oldFolderChildren) {
		if (oldFolderChild.getPosition() >= oldPosition) {
		    oldFolderChild.setPosition(oldFolderChild.getPosition() - 1);
		    nodeRepository.save(oldFolderChild);
		}
	    }
	}
    }
    
    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects", method = RequestMethod.GET)
    public @ResponseBody
    List<BaseNode> findProjectsFromAWorkspace(@PathVariable("workspaceId") String workspaceId,
	    @RequestParam(value = "search", required = false) String search) {
	return nodeRepository.findProjectsfromAWorkspace(workspaceId, search != null ? search : "");
    }
}
