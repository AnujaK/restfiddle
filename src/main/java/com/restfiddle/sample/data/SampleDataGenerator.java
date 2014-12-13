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
package com.restfiddle.sample.data;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restfiddle.constant.NodeType;
import com.restfiddle.constant.PermissionType;
import com.restfiddle.constant.RoleType;
import com.restfiddle.controller.rest.ConfigController;
import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.controller.rest.PermissionController;
import com.restfiddle.controller.rest.ProjectController;
import com.restfiddle.controller.rest.RoleController;
import com.restfiddle.controller.rest.TagController;
import com.restfiddle.controller.rest.UserController;
import com.restfiddle.controller.rest.WorkspaceController;
import com.restfiddle.dao.UserRepository;
import com.restfiddle.dto.ConfigDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.PasswordDTO;
import com.restfiddle.dto.PermissionDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RoleDTO;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.dto.WorkspaceDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Config;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.User;
import com.restfiddle.util.CommonUtil;

@Component
public class SampleDataGenerator {

    @Autowired
    private ConfigController configController;

    @Autowired
    private RoleController roleController;

    @Autowired
    private PermissionController permissionController;

    @Autowired
    private UserController userController;

    @Autowired
    private WorkspaceController workspaceController;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private NodeController nodeController;

    @Autowired
    private ConversationController conversationController;

    @Autowired
    private TagController tagController;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() {
	if (isSampleDataPresent()) {
	    return;
	}

	createSuperUser();

	// loadRoleData();
	// loadPermissionData();
	// loadUserData();
	loadWorkspaceData();
	loadProjectData();
	
	loadTagData();
	
	loadNodeData();
    }

    private void loadRoleData() {
	RoleDTO adminRoleDTO = new RoleDTO();
	adminRoleDTO.setType(RoleType.ROLE_ADMIN.name());
	roleController.create(adminRoleDTO);

	RoleDTO userRoleDTO = new RoleDTO();
	userRoleDTO.setType(RoleType.ROLE_USER.name());
	roleController.create(userRoleDTO);
    }

    private void loadPermissionData() {
	PermissionDTO viewWorkspacePermission = new PermissionDTO();
	viewWorkspacePermission.setType(PermissionType.VIEW_WORKSPACE.name());
	permissionController.create(viewWorkspacePermission);

	PermissionDTO modifyWorkspacePermission = new PermissionDTO();
	modifyWorkspacePermission.setType(PermissionType.MODIFY_WORKSPACE.name());
	permissionController.create(modifyWorkspacePermission);

	PermissionDTO createWorkspacePermission = new PermissionDTO();
	createWorkspacePermission.setType(PermissionType.CREATE_WORKSPACE.name());
	permissionController.create(createWorkspacePermission);

	PermissionDTO deleteWorkspacePermission = new PermissionDTO();
	deleteWorkspacePermission.setType(PermissionType.DELETE_WORKSPACE.name());
	permissionController.create(deleteWorkspacePermission);

	PermissionDTO viewProject = new PermissionDTO();
	viewProject.setType(PermissionType.VIEW_PROJECT.name());
	permissionController.create(viewProject);

	PermissionDTO modifyProjectPermission = new PermissionDTO();
	modifyProjectPermission.setType(PermissionType.MODIFY_PROJECT.name());
	permissionController.create(modifyProjectPermission);

	PermissionDTO createProjectPermission = new PermissionDTO();
	createProjectPermission.setType(PermissionType.CREATE_PROJECT.name());
	permissionController.create(createProjectPermission);

	PermissionDTO deleteProjectPermission = new PermissionDTO();
	deleteProjectPermission.setType(PermissionType.DELETE_PROJECT.name());
	permissionController.create(deleteProjectPermission);
    }

    private void createSuperUser() {
	User user = userRepository.findByEmail("rf@example.com");
	if (CommonUtil.isNotNull(user)) {
	    return;
	}

	PasswordDTO user1 = new PasswordDTO();
	user1.setName("RF Admin");
	user1.setEmail("rf@example.com");
	user1.setPassword("rf");
	userController.create(user1);

	PasswordDTO user2 = new PasswordDTO();
	user2.setName("Ranjan Kumar");
	user2.setEmail("ranjan@example.com");
	user2.setPassword("rf");
	userController.create(user2);

    }

    private void loadUserData() {

    }

    private boolean isSampleDataPresent() {
	// TODO : find sampleDataConfig by key
	Config sampleDataConfig = configController.findById(1L);

	if (sampleDataConfig == null || sampleDataConfig.getConfigKey() == null) {
	    ConfigDTO configDTO = new ConfigDTO();
	    configDTO.setName("Sample Data Present");
	    configDTO.setConfigKey("SAMPLE_DATA_PRESENT");
	    configController.create(configDTO);
	    return false;
	}
	return true;
    }

    private void loadWorkspaceData() {
	WorkspaceDTO demoWorkspace = new WorkspaceDTO();
	demoWorkspace.setName("Demo Workspace");
	demoWorkspace.setDescription("This is a demo workspace");
	workspaceController.create(demoWorkspace);

	WorkspaceDTO socialWorkspace = new WorkspaceDTO();
	socialWorkspace.setName("Social Workspace");
	socialWorkspace.setDescription("This is my social workspace");
	workspaceController.create(socialWorkspace);
    }

    private void loadProjectData() {
	ProjectDTO firstProject = new ProjectDTO();
	firstProject.setName("My First Project");
	projectController.create(1L, firstProject);

	ProjectDTO secondProject = new ProjectDTO();
	secondProject.setName("My Second Project");
	projectController.create(1L, secondProject);

	ProjectDTO googleProject = new ProjectDTO();
	googleProject.setName("Google");
	projectController.create(2L, googleProject);

	ProjectDTO facebookProject = new ProjectDTO();
	facebookProject.setName("Facebook");
	projectController.create(2L, facebookProject);

	ProjectDTO twitterProject = new ProjectDTO();
	twitterProject.setName("Twitter");
	projectController.create(2L, twitterProject);

	ProjectDTO linkedinProject = new ProjectDTO();
	linkedinProject.setName("LinkedIn");
	projectController.create(2L, linkedinProject);
    }

    private void loadNodeData() {
        TagDTO tag1 = new TagDTO();
        tag1.setId(1L);
        TagDTO tag2 = new TagDTO();
        tag2.setId(2L);
	ArrayList<TagDTO> tags = new ArrayList<TagDTO>();
	tags.add(tag1);
	tags.add(tag2);
	
	NodeDTO firstFolderNode = new NodeDTO();
	firstFolderNode.setName("First Folder Node");
	firstFolderNode.setNodeType(NodeType.FOLDER.name());
	firstFolderNode.setProjectId(1L);

	ConversationDTO conversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	ConversationDTO postConversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO2 = new RfRequestDTO();
	rfRequestDTO2.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO2.setMethodType("POST");
	rfRequestDTO2.setApiBody("{\"name\" : \"Test Worksapce\", \"description\" : \"This is test workspace from sample data generator\"}");
	postConversationDTO.setRfRequestDTO(rfRequestDTO2);

	Conversation createdConversation = conversationController.create(conversationDTO);
	Conversation createdPostConversation = conversationController.create(postConversationDTO);
	conversationDTO.setId(createdConversation.getId());
	postConversationDTO.setId(createdPostConversation.getId());
	// firstFolderNode.setConversationDTO(conversationDTO);

	BaseNode createdFolderNode = nodeController.create(1L, firstFolderNode);

	NodeDTO childNode = new NodeDTO();
	childNode.setName("GET Workspace");
	childNode.setDescription("A workspace is a collection of projects. This API returns list of available workspaces.");
	childNode.setProjectId(1L);
	childNode.setConversationDTO(conversationDTO);
	BaseNode createdChildNode = nodeController.create(createdFolderNode.getId(), childNode);
	nodeController.addTags(createdChildNode.getId(), tags);

	NodeDTO secondNode = new NodeDTO();
	secondNode.setName("POST Worksapce");
	secondNode.setDescription("A workspace is a collection of projects. This API is used to create a new workspace.");
	secondNode.setProjectId(1L);
	secondNode.setConversationDTO(postConversationDTO);
	BaseNode createdSecondNode = nodeController.create(1L, secondNode);
	nodeController.addTags(createdSecondNode.getId(), tags);

	NodeDTO dummyNode = new NodeDTO();
	dummyNode.setName("Dummy Node");
	dummyNode.setProjectId(1L);
	nodeController.create(1L, dummyNode);

	NodeDTO testNode = new NodeDTO();
	testNode.setName("Test Node");
	testNode.setProjectId(1L);
	nodeController.create(1L, testNode);

	conversationDTO = new ConversationDTO();
	rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/nodes/starred");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);
	createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());
	
	NodeDTO starredNode = new NodeDTO();
	starredNode.setName("Starred Node");
	starredNode.setDescription("This API returns a list of requests which are marked as star.");
	starredNode.setStarred(Boolean.TRUE);
	starredNode.setProjectId(1L);
	starredNode.setConversationDTO(conversationDTO);

	nodeController.create(1L, starredNode);
    }

    private void loadTagData() {
	TagDTO tagDTO = new TagDTO();
	tagDTO.setName("Important");
	tagController.create(tagDTO);

	TagDTO secondTag = new TagDTO();
	secondTag.setName("Wishlist");
	tagController.create(secondTag);
    }
}
