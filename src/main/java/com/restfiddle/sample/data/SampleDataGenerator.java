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
import com.restfiddle.controller.rest.ProjectController;
import com.restfiddle.controller.rest.WorkspaceController;
import com.restfiddle.dao.util.NodeTypes;
import com.restfiddle.dto.ConfigDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.WorkspaceDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Config;
import com.restfiddle.entity.Conversation;

@Component
public class SampleDataGenerator {

    @Autowired
    private ConfigController configController;

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
	loadWorkspaceData();
	loadProjectData();
	loadNodeData();
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

	ConversationDTO conversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	Conversation createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());
	firstFolderNode.setConversationDTO(conversationDTO);
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
    }
}
