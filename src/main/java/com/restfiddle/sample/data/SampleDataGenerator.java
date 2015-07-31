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
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
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
import com.restfiddle.dao.HttpRequestHeaderRepository;
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
import com.restfiddle.entity.Config;
import com.restfiddle.entity.HttpRequestHeader;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.Tag;
import com.restfiddle.entity.User;
import com.restfiddle.entity.Workspace;
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

    @Autowired
    private HttpRequestHeaderRepository requestHeaderRepository;

    @Autowired
    private OAuth2DataGenerator oauth2DataGenerator;

    private String demoWorkspaceId;
    private String socialWorkspaceId;

    private String firstProjectId;
    private String firstProjectRefId;

    private String impTagId;
    private String wlTagId;

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

	loadHttpRequestHeaders();

	oauth2DataGenerator.loadAsanaAPI();
	oauth2DataGenerator.loadTrelloAPI();
	oauth2DataGenerator.loadGitHubAPI();
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
	List<Config> configList = configController.findAll();

	if (configList == null || configList.size() == 0) {
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
	Workspace workspace1 = workspaceController.create(demoWorkspace);
	demoWorkspaceId = workspace1.getId();

	WorkspaceDTO socialWorkspace = new WorkspaceDTO();
	socialWorkspace.setName("Social Workspace");
	socialWorkspace.setDescription("This is my social workspace");
	Workspace workspace2 = workspaceController.create(socialWorkspace);
	socialWorkspaceId = workspace2.getId();
    }

    private void loadProjectData() {
	ProjectDTO firstProject = new ProjectDTO();
	firstProject.setName("My First Project");
	Project proj1 = projectController.create(demoWorkspaceId, firstProject);
	firstProjectId = proj1.getId();
	firstProjectRefId = proj1.getProjectRef().getId();

	ProjectDTO secondProject = new ProjectDTO();
	secondProject.setName("My Second Project");
	projectController.create(demoWorkspaceId, secondProject);

	ProjectDTO googleProject = new ProjectDTO();
	googleProject.setName("Google");
	projectController.create(socialWorkspaceId, googleProject);

	ProjectDTO facebookProject = new ProjectDTO();
	facebookProject.setName("Facebook");
	projectController.create(socialWorkspaceId, facebookProject);

	ProjectDTO twitterProject = new ProjectDTO();
	twitterProject.setName("Twitter");
	projectController.create(socialWorkspaceId, twitterProject);

	ProjectDTO linkedinProject = new ProjectDTO();
	linkedinProject.setName("LinkedIn");
	projectController.create(socialWorkspaceId, linkedinProject);
    }

    private void loadNodeData() {
	TagDTO tag1 = new TagDTO();
	tag1.setId(impTagId);
	TagDTO tag2 = new TagDTO();
	tag2.setId(wlTagId);
	ArrayList<TagDTO> tags = new ArrayList<TagDTO>();
	tags.add(tag1);
	tags.add(tag2);

	NodeDTO firstFolderNode = new NodeDTO();
	firstFolderNode.setName("First Folder Node");
	firstFolderNode.setNodeType(NodeType.FOLDER.name());
	firstFolderNode.setProjectId(firstProjectId);

	ConversationDTO conversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	ConversationDTO postConversationDTO = new ConversationDTO();
	RfRequestDTO rfRequestDTO2 = new RfRequestDTO();
	rfRequestDTO2.setApiUrl("http://localhost:8080/api/workspaces");
	rfRequestDTO2.setMethodType("POST");

	JSONObject jsonObject = new JSONObject();
	jsonObject.put("name", "Test Workspace");
	jsonObject.put("description", "This is test workspace from sample data generator");

	rfRequestDTO2.setApiBody(jsonObject.toString(4));
	postConversationDTO.setRfRequestDTO(rfRequestDTO2);

	ConversationDTO createdConversation = conversationController.create(conversationDTO);
	ConversationDTO createdPostConversation = conversationController.create(postConversationDTO);
	
	// firstFolderNode.setConversationDTO(conversationDTO);

	NodeDTO createdFolderNode = nodeController.create(firstProjectRefId, firstFolderNode);

	NodeDTO childNode = new NodeDTO();
	childNode.setName("GET Workspace");
	childNode.setDescription("A workspace is a collection of projects. This API returns list of available workspaces.");
	childNode.setProjectId(firstProjectId);
	childNode.setConversationDTO(createdConversation);
	NodeDTO createdChildNode = nodeController.create(createdFolderNode.getId(), childNode);
	nodeController.addTags(createdChildNode.getId(), tags);
	createdConversation.setNodeDTO(createdChildNode);
	conversationController.update(createdConversation.getId(), createdConversation);

	NodeDTO secondNode = new NodeDTO();
	secondNode.setName("POST Workspace");
	secondNode.setDescription("A workspace is a collection of projects. This API is used to create a new workspace.");
	secondNode.setProjectId(firstProjectId);
	secondNode.setConversationDTO(createdPostConversation);
	NodeDTO createdSecondNode = nodeController.create(firstProjectRefId, secondNode);
	nodeController.addTags(createdSecondNode.getId(), tags);
	createdPostConversation.setNodeDTO(createdSecondNode);
	conversationController.update(createdPostConversation.getId(), createdPostConversation);

	NodeDTO dummyNode = new NodeDTO();
	dummyNode.setName("Dummy Node");
	dummyNode.setProjectId(firstProjectId);
	nodeController.create(firstProjectRefId, dummyNode);

	NodeDTO testNode = new NodeDTO();
	testNode.setName("Test Node");
	testNode.setProjectId(firstProjectId);
	nodeController.create(firstProjectRefId, testNode);

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
	starredNode.setProjectId(firstProjectId);
	starredNode.setConversationDTO(conversationDTO);
	NodeDTO createdStarredNode = nodeController.create(firstProjectRefId, starredNode);
	createdConversation.setNodeDTO(createdStarredNode);
	conversationController.update(createdConversation.getId(), createdConversation);
    }

    private void loadTagData() {
	TagDTO tagDTO = new TagDTO();
	tagDTO.setName("Important");
	Tag impTag = tagController.create(demoWorkspaceId, tagDTO);
	impTagId = impTag.getId();

	TagDTO secondTag = new TagDTO();
	secondTag.setName("Wishlist");
	Tag wlTag = tagController.create(socialWorkspaceId, secondTag);
	wlTagId = wlTag.getId();
    }

    private void loadHttpRequestHeaders() {
	String[] headersArr = { "Accept", "Accept-Charset", "Accept-Encoding", "Accept-Language", "Accept-Datetime", "Authorization",
		"Cache-Control", "Connection", "Cookie", "Content-Length", "Content-MD5", "Content-Type", "Date", "Expect", "From", "Host",
		"If-Match", "If-Modified-Since", "If-None-Match", "If-Range", "If-Unmodified-Since", "Max-Forwards", "Origin", "Pragma",
		"Proxy-Authorization", "Range", "Referer", "TE", "User-Agent", "Upgrade", "Via", "Warning", "X-Requested-With", "DNT",
		"X-Forwarded-For", "X-Forwarded-Host", "X-Forwarded-Proto", "Front-End-Https", "X-Http-Method-Override", "X-ATT-DeviceId",
		"X-Wap-Profile", "Proxy-Connection" };

	List<HttpRequestHeader> headers = new ArrayList<HttpRequestHeader>();
	HttpRequestHeader header = null;

	for (int i = 0; i < headersArr.length; i++) {
	    header = new HttpRequestHeader();
	    header.setName(headersArr[i]);
	    headers.add(header);
	}

	requestHeaderRepository.save(headers);
    }
}
