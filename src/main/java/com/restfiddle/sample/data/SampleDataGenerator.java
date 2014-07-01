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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restfiddle.controller.rest.ConfigController;
import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.controller.rest.PermissionController;
import com.restfiddle.controller.rest.ProjectController;
import com.restfiddle.controller.rest.RoleController;
import com.restfiddle.controller.rest.UserController;
import com.restfiddle.controller.rest.WorkspaceController;
import com.restfiddle.dao.util.NodeTypes;
import com.restfiddle.dao.util.PermissionTypes;
import com.restfiddle.dao.util.RoleTypes;
import com.restfiddle.dto.ConfigDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.PermissionDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RoleDTO;
import com.restfiddle.dto.UserDTO;
import com.restfiddle.dto.WorkspaceDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Config;
import com.restfiddle.entity.Conversation;

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

    @PostConstruct
    public void initialize() {
	if (isSampleDataPresent()) {
	    return;
	}
	loadRoleData();
	loadPermissionData();
	loadUserData();
	loadWorkspaceData();
	loadProjectData();
	loadNodeData();
    }

    private void loadRoleData() {
	RoleDTO adminRoleDTO = new RoleDTO();
	adminRoleDTO.setType(RoleTypes.ROLE_ADMIN.name());
	roleController.create(adminRoleDTO);

	RoleDTO userRoleDTO = new RoleDTO();
	userRoleDTO.setType(RoleTypes.ROLE_USER.name());
	roleController.create(userRoleDTO);
    }

    private void loadPermissionData() {
	PermissionDTO viewWorkspacePermission = new PermissionDTO();
	viewWorkspacePermission.setType(PermissionTypes.VIEW_WORKSPACE.name());
	permissionController.create(viewWorkspacePermission);

	PermissionDTO modifyWorkspacePermission = new PermissionDTO();
	modifyWorkspacePermission.setType(PermissionTypes.MODIFY_WORKSPACE.name());
	permissionController.create(modifyWorkspacePermission);

	PermissionDTO createWorkspacePermission = new PermissionDTO();
	createWorkspacePermission.setType(PermissionTypes.CREATE_WORKSPACE.name());
	permissionController.create(createWorkspacePermission);

	PermissionDTO deleteWorkspacePermission = new PermissionDTO();
	deleteWorkspacePermission.setType(PermissionTypes.DELETE_WORKSPACE.name());
	permissionController.create(deleteWorkspacePermission);

	PermissionDTO viewProject = new PermissionDTO();
	viewProject.setType(PermissionTypes.VIEW_PROJECT.name());
	permissionController.create(viewProject);

	PermissionDTO modifyProjectPermission = new PermissionDTO();
	modifyProjectPermission.setType(PermissionTypes.MODIFY_PROJECT.name());
	permissionController.create(modifyProjectPermission);

	PermissionDTO createProjectPermission = new PermissionDTO();
	createProjectPermission.setType(PermissionTypes.CREATE_PROJECT.name());
	permissionController.create(createProjectPermission);

	PermissionDTO deleteProjectPermission = new PermissionDTO();
	deleteProjectPermission.setType(PermissionTypes.DELETE_PROJECT.name());
	permissionController.create(deleteProjectPermission);
    }

    private void loadUserData() {
	UserDTO adminUserDTO = new UserDTO();
	adminUserDTO.setFirstName("Rest");
	adminUserDTO.setLastName("Fiddle");
	userController.create(adminUserDTO);

	UserDTO userDTO = new UserDTO();
	userDTO.setFirstName("Ranjan");
	userDTO.setLastName("Kumar");
	userController.create(userDTO);
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
	NodeDTO firstFolderNode = new NodeDTO();
	firstFolderNode.setName("First Folder Node");
	firstFolderNode.setNodeType(NodeTypes.FOLDER.name());
	firstFolderNode.setProjectId(1L);
	BaseNode createdFolderNode = nodeController.create(1L, firstFolderNode);

	NodeDTO childNode = new NodeDTO();
	childNode.setName("Child Node");
	childNode.setProjectId(1L);
	nodeController.create(createdFolderNode.getId(), childNode);

	NodeDTO secondNode = new NodeDTO();
	secondNode.setName("Second Node");
	secondNode.setProjectId(1L);
	nodeController.create(1L, secondNode);

	NodeDTO dummyNode = new NodeDTO();
	dummyNode.setName("Dummy Node");
	dummyNode.setProjectId(1L);
	nodeController.create(1L, dummyNode);

	NodeDTO testNode = new NodeDTO();
	testNode.setName("Test Node");
	testNode.setProjectId(1L);
	nodeController.create(1L, testNode);

	ConversationDTO conversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	Conversation createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());
	
	NodeDTO starredNode = new NodeDTO();
	starredNode.setName("Starred Node");
	starredNode.setStarred(Boolean.TRUE);
	starredNode.setProjectId(1L);
	starredNode.setConversationDTO(conversationDTO);

	nodeController.create(1L, starredNode);
    }
}
