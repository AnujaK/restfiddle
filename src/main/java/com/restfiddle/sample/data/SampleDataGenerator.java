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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
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
import com.restfiddle.dto.BasicAuthDTO;
import com.restfiddle.dto.ConfigDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.PasswordDTO;
import com.restfiddle.dto.PermissionDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RoleDTO;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.dto.UrlParamDTO;
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

    private static final String HTTPBIN_PROJECT = "httpbinProject";

    @Value("${application.host-uri}")
    private String hostUri;

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
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private String demoWorkspaceId;
    private String socialWorkspaceId;

    private String firstProjectId;
    private String firstProjectRefId;
    
    @SuppressWarnings("unused")
    private String httpbinProjectId;
    @SuppressWarnings("unused")
    private String httpbinProjectRefId;
    @SuppressWarnings("unused")
    private String gitProjectId;
    @SuppressWarnings("unused")
    private String gitProjectRefId;

    private String impTagId;
    private String wlTagId;
    private String sampleTagId;
    
    ArrayList<TagDTO> tags;

    @PostConstruct
    public void initialize() {
	if (isSampleDataPresent()) {
	    return;
	}
	
	addIndexEntityAuth();

	createSuperUser();

	loadWorkspaceData();

	loadProjectData();

	loadTagData();

	loadNodeData();

	loadHttpRequestHeaders();

	oauth2DataGenerator.loadAsanaAPI();
	oauth2DataGenerator.loadTrelloAPI();
	oauth2DataGenerator.loadGitHubAPI();
	oauth2DataGenerator.loadGoogleAPI();
	oauth2DataGenerator.loadFacebookAPI();
    }

    @SuppressWarnings("unused")
    private void loadRoleData() {
	RoleDTO adminRoleDTO = new RoleDTO();
	adminRoleDTO.setType(RoleType.ROLE_ADMIN.name());
	roleController.create(adminRoleDTO);

	RoleDTO userRoleDTO = new RoleDTO();
	userRoleDTO.setType(RoleType.ROLE_USER.name());
	roleController.create(userRoleDTO);
    }

    @SuppressWarnings("unused")
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
	user2.setName("Anuja Kumar");
	user2.setEmail("anuja@example.com");
	user2.setPassword("rf");
	userController.create(user2);

    }

    @SuppressWarnings("unused")
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
	
	ProjectDTO httpbinProject = new ProjectDTO();
	httpbinProject.setName("httpbin Project");
	Project projHttpbin = projectController.create(demoWorkspaceId, httpbinProject);
	httpbinProjectId = projHttpbin.getId();
	httpbinProjectRefId = projHttpbin.getProjectRef().getId();
	
	ProjectDTO gitProject = new ProjectDTO();
	gitProject.setName("Git Project");
	Project projGit = projectController.create(demoWorkspaceId, gitProject);
	gitProjectId = projGit.getId();
	gitProjectRefId = projGit.getProjectRef().getId();

	ProjectDTO googleProject = new ProjectDTO();
	googleProject.setName("Google");
	Project projGoogle = projectController.create(socialWorkspaceId, googleProject);
	createSocialSample(projGoogle, "https://www.googleapis.com/plus/v1/people/me", "GET", "Google Plus", "Get Access Token from Auth and run this request. Enable Google Plus API in your account https://console.developers.google.com.", null, null);

	ProjectDTO facebookProject = new ProjectDTO();
	facebookProject.setName("Facebook");
	Project projFacebook = projectController.create(socialWorkspaceId, facebookProject);
	createSocialSample(projFacebook, "https://graph.facebook.com/me", "GET", "Graph API", "Get Access Token from Auth and run this request. Do needful settings for your app in https://developers.facebook.com", null, null);

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
	TagDTO tag3 = new TagDTO();
	tag3.setId(sampleTagId);
	tags = new ArrayList<TagDTO>();
	tags.add(tag1);
	tags.add(tag2);
	tags.add(tag3);

	NodeDTO firstFolderNode = new NodeDTO();
	firstFolderNode.setName("First Folder Node");
	firstFolderNode.setNodeType(NodeType.FOLDER.name());
	firstFolderNode.setProjectId(firstProjectId);
	firstFolderNode.setWorkspaceId(demoWorkspaceId);

	ConversationDTO conversationDTO = new ConversationDTO();
	conversationDTO.setWorkspaceId(demoWorkspaceId);
	
	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl(hostUri + "/api/workspaces");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);

	ConversationDTO postConversationDTO = new ConversationDTO();
	postConversationDTO.setWorkspaceId(demoWorkspaceId);
	
	RfRequestDTO rfRequestDTO2 = new RfRequestDTO();
	rfRequestDTO2.setApiUrl(hostUri + "/api/workspaces");
	rfRequestDTO2.setMethodType("POST");

	JSONObject jsonObject = new JSONObject();
	jsonObject.put("name", "Test Workspace");
	jsonObject.put("description", "This is test workspace from sample data generator");

	rfRequestDTO2.setApiBody(jsonObject.toString(4));
	postConversationDTO.setRfRequestDTO(rfRequestDTO2);

	ConversationDTO createdConversation = conversationController.create(conversationDTO);
	ConversationDTO createdPostConversation = conversationController.create(postConversationDTO);
	
	NodeDTO createdFolderNode = nodeController.create(firstProjectRefId, firstFolderNode);

	NodeDTO childNode = new NodeDTO();
	childNode.setName("GET Workspace");
	childNode.setDescription("A workspace is a collection of projects. This API returns list of available workspaces.");
	childNode.setProjectId(firstProjectId);
	childNode.setWorkspaceId(demoWorkspaceId);
	childNode.setConversationDTO(createdConversation);
	NodeDTO createdChildNode = nodeController.create(createdFolderNode.getId(), childNode);
	nodeController.addTags(createdChildNode.getId(), tags);
	createdConversation.setNodeDTO(createdChildNode);
	conversationController.update(createdConversation.getId(), createdConversation);

	NodeDTO secondNode = new NodeDTO();
	secondNode.setName("POST Workspace");
	secondNode.setDescription("A workspace is a collection of projects. This API is used to create a new workspace.");
	secondNode.setProjectId(firstProjectId);
	secondNode.setWorkspaceId(demoWorkspaceId);
	secondNode.setConversationDTO(createdPostConversation);
	NodeDTO createdSecondNode = nodeController.create(firstProjectRefId, secondNode);
	nodeController.addTags(createdSecondNode.getId(), tags);
	createdPostConversation.setNodeDTO(createdSecondNode);
	conversationController.update(createdPostConversation.getId(), createdPostConversation);

	conversationDTO = new ConversationDTO();
	conversationDTO.setWorkspaceId(demoWorkspaceId);
	
	rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl(hostUri + "/api/workspaces/"+demoWorkspaceId+"/projects");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);
	createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());

	NodeDTO testNode = new NodeDTO();
	testNode.setName("Test Node");
	testNode.setDescription("Fetches projects from a workspace.");
	testNode.setProjectId(firstProjectId);
	testNode.setWorkspaceId(demoWorkspaceId);
	testNode.setConversationDTO(conversationDTO);
	NodeDTO createdTestNode = nodeController.create(firstProjectRefId, testNode);
	createdConversation.setNodeDTO(createdTestNode);
	conversationController.update(createdConversation.getId(), createdConversation);

	conversationDTO = new ConversationDTO();
	conversationDTO.setWorkspaceId(demoWorkspaceId);
	
	rfRequestDTO = new RfRequestDTO();
	rfRequestDTO.setApiUrl(hostUri + "/api/workspaces/"+demoWorkspaceId+"/nodes/starred");
	rfRequestDTO.setMethodType("GET");
	conversationDTO.setRfRequestDTO(rfRequestDTO);
	createdConversation = conversationController.create(conversationDTO);
	conversationDTO.setId(createdConversation.getId());

	NodeDTO starredNode = new NodeDTO();
	starredNode.setName("Starred Node");
	starredNode.setDescription("This API returns a list of requests which are marked as star.");
	starredNode.setStarred(Boolean.TRUE);
	starredNode.setProjectId(firstProjectId);
	starredNode.setWorkspaceId(demoWorkspaceId);
	starredNode.setConversationDTO(conversationDTO);
	NodeDTO createdStarredNode = nodeController.create(firstProjectRefId, starredNode);
	createdConversation.setNodeDTO(createdStarredNode);
	conversationController.update(createdConversation.getId(), createdConversation);

	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/ip", "GET", "httpbin ip", "Returns Origin IP.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/user-agent", "GET", "httpbin User Agent", "Returns user-agent.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/headers", "GET", "httpbin Headers", "Returns header dict.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/get", "GET", "httpbin Get", "Returns GET data.", null, null, null);
	UrlParamDTO urlParamDTO = new UrlParamDTO();
	urlParamDTO.setKey("key1");
	urlParamDTO.setValue("value1");
	List<UrlParamDTO> urlParams = new ArrayList<UrlParamDTO>();
	urlParams.add(urlParamDTO);
	JSONObject jsonObjectSample = new JSONObject();
	jsonObjectSample.put("name", "httpbin Post");
	jsonObjectSample.put("description", "Test request using sample data generator");
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/post", "POST", "Post", "POST method testing.", urlParams, jsonObjectSample, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/encoding/utf8", "GET", "UTF-8", "Returns page containing UTF-8 data.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/delete", "DELETE", "Delete", "Returns DELETE data.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/gzip", "GET", "Gzip", "Returns gzip-encoded data.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/deflate", "GET", "Deflate", "Returns deflate-encoded data.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/status/418", "GET", "Status code", "Returns given HTTP Status code.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/response-headers?Content-Type=text/plain;charset=UTF-8&Server=httpbin", "GET", "httpbin Content type", "Returns given response headers.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/redirect/6", "GET", "httpbin redirect", "Redirects.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/redirect-to/url=http://example.com/", "GET", "Redirect-to", "Redirects to the url.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/relative-redirect/6", "GET", "Relative redirect", "Relative Redirect.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/absolute-redirect/6", "GET", "Absolute redirect", "Absolute Redirect.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/cookies", "GET", "Cookies", "Returns cookie data.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/cookies/set?k2=v2&k1=v1", "GET", "Simple Cookies", "Sets one or more simple cookies.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/cookies/delete?k2=&k1=", "GET", "Delete Cookies", "Deletes one or more simple cookies.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/basic-auth/user/passwd", "GET", "Basic Auth", "Challenges HTTPBasic Auth.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/hidden-basic-auth/user/passwd", "GET", "Hidden Basic Auth", "404'd BasicAuth.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/digest-basic-auth/auth/user/passwd", "GET", "Digest Auth", "Challenges HTTP Digest Auth.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/stream/20", "GET", "Digest Auth", "Streams n–100 lines.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/delay/3", "GET", "Delay", "Delays responding for n–10 seconds.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/drip?duration=5&numbytes=5&code=200", "GET", "Drip", "Drips data over a duration after an optional initial delay, then (optionally) returns with the given status code.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/range/1024", "GET", "Range", "Streams n bytes, and allows specifying a Range header to select a subset of the data. Accepts a chunk_size and request duration parameter.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/html", "GET", "HTML", "Renders an HTML Page.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/robots.txt", "GET", "Robots.txt", "Returns some robots.txt rules.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/deny", "GET", "Deny", "Denied by robots.txt file.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/cache", "GET", "Cache", "Returns 200 unless an If-Modified-Since or If-None-Match header is provided, when it returns a 304.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/cache/60", "GET", "Cache-Control", "Sets a Cache-Control header for n seconds.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/bytes/1024", "GET", "Bytes", "Generates n random bytes of binary data, accepts optional seed integer parameter.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/stream-bytes/1024", "GET", "Stream-bytes", "Streams n random bytes of binary data, accepts optional seed and chunk_size integer parameters.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/links/10", "GET", "Bytes", "Returns page containing n HTML links.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/image", "GET", "Image", "Returns page containing an image.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/image/png", "GET", "Image PNG", "Returns page containing PNG image.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/image/jpeg", "GET", "Image JPEG", "Returns page containing JPEG image.", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/image/webp", "GET", "Image", "Returns page containing WEBP image.", null, null, null);
	//CheckPost form returns the form but doesn't return response on submit.
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/forms/post", "GET", "Post form", "HTML form that submits to /post", null, null, null);
	createSampleRequest(HTTPBIN_PROJECT, "http://httpbin.org/xml", "GET", "XML", "Returns some XML", null, null, null);
	
	createSampleRequest("gitProject", "https://api.github.com/users/anujak", "GET", "Git Profile", "Get Git profile by username", null, null, null);
	BasicAuthDTO basicAuth = new BasicAuthDTO();
	basicAuth.setUsername("username");
	basicAuth.setPassword("password");
	createSampleRequest("gitProject", "https://api.github.com/user", "GET", "Basic Auth Logged in User Profile", "Get Git profile of logged in user by basic authentication", null, null, basicAuth);
    }

    private void createSampleRequest(String projectName, String apiUrl, String methodType, String name, String description, List<UrlParamDTO> urlParams, JSONObject jsonObject, BasicAuthDTO basicAuth) {
	String projId = null;
	String projRefId = null;
	try {
	    Field declaredField = this.getClass().getDeclaredField(projectName+"Id");
	    projId = (String) declaredField.get(this);
	    Field declaredField2 = this.getClass().getDeclaredField(projectName+"RefId");
	    projRefId = (String) declaredField2.get(this);
	} catch (NoSuchFieldException | SecurityException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
	
	ConversationDTO conversationDTO = new ConversationDTO();
	conversationDTO.setWorkspaceId(demoWorkspaceId);
	
	RfRequestDTO requestDTO = new RfRequestDTO();
	requestDTO.setApiUrl(apiUrl);
	requestDTO.setMethodType(methodType);
	
	conversationDTO.setRfRequestDTO(requestDTO);
	if (urlParams!=null) {
	    requestDTO.setUrlParams(urlParams);
	}
	if(jsonObject!=null){
	    requestDTO.setApiBody(jsonObject.toString(4));
	}
	if(basicAuth!=null){
	    requestDTO.setBasicAuthDTO(basicAuth);
	}
	ConversationDTO conversation = conversationController.create(conversationDTO);
	NodeDTO node = new NodeDTO();
	node.setName(name);
	node.setDescription(description);
	node.setProjectId(projId);
	node.setConversationDTO(conversation);
	node.setWorkspaceId(demoWorkspaceId);
	NodeDTO createdHttpbinNode = nodeController.create(projRefId, node);
	conversation.setNodeDTO(createdHttpbinNode);
	conversationController.update(conversation.getId(), conversation);
    }
    
    private void createSocialSample(Project project, String apiUrl, String methodType, String name, String description, List<UrlParamDTO> urlParams, JSONObject jsonObject) {
	String projectId = project.getId();
	String projectRefId = project.getProjectRef().getId();
	
	ConversationDTO socialDTO = new ConversationDTO();
	socialDTO.setWorkspaceId(socialWorkspaceId);
	
	RfRequestDTO socialReqDTO = new RfRequestDTO();
   	socialReqDTO.setApiUrl(apiUrl);
   	socialReqDTO.setMethodType(methodType);
   	socialDTO.setRfRequestDTO(socialReqDTO);
   	if (urlParams!=null) {
   	    socialReqDTO.setUrlParams(urlParams);
   	}
   	if(jsonObject!=null){
   	    socialReqDTO.setApiBody(jsonObject.toString(4));
   	}
   	ConversationDTO conversationsocial = conversationController.create(socialDTO);
   	NodeDTO socialNode = new NodeDTO();
   	socialNode.setName(name);
   	socialNode.setDescription(description);
   	socialNode.setProjectId(projectId);
   	socialNode.setConversationDTO(conversationsocial);
   	socialNode.setWorkspaceId(socialWorkspaceId);
   	NodeDTO createdSocialNode = nodeController.create(projectRefId, socialNode);
   	conversationsocial.setNodeDTO(createdSocialNode);
   	conversationController.update(conversationsocial.getId(), conversationsocial);
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
	
	TagDTO tagDTO2 = new TagDTO();
	tagDTO2.setName("Sample");
	Tag sampleTag = tagController.create(demoWorkspaceId, tagDTO2);
	sampleTagId = sampleTag.getId();
    }

    private void loadHttpRequestHeaders() {
	String[] headersArr = { "Accept", "Accept-Charset", "Accept-Encoding", "Accept-Language", "Accept-Datetime", "Authorization",
		"Cache-Control", "Connection", "Cookie", "Content-Length", "Content-MD5", "Content-Type", "Date", "Expect", "From", "Host",
		"If-Match", "If-Modified-Since", "If-None-Match", "If-Range", "If-Unmodified-Since", "Max-Forwards", "Origin", "Pragma",
		"Proxy-Authorization", "Range", "Referer", "TE", "User-Agent", "Upgrade", "Via", "Warning", "X-Requested-With", "DNT",
		"X-Forwarded-For", "X-Forwarded-Host", "X-Forwarded-Proto", "Front-End-Https", "X-Http-Method-Override", "X-ATT-DeviceId",
		"X-Wap-Profile", "Proxy-Connection" };

	List<HttpRequestHeader> headers = new ArrayList<HttpRequestHeader>();
	HttpRequestHeader header;

	for (int i = 0; i < headersArr.length; i++) {
	    header = new HttpRequestHeader();
	    header.setName(headersArr[i]);
	    headers.add(header);
	}

	requestHeaderRepository.save(headers);
    }
    
    private void addIndexEntityAuth(){
	DBCollection dbCollectionAuth = mongoTemplate.getCollection("EntityAuth");
	dbCollectionAuth.createIndex(new BasicDBObject("expireAt", 1),new BasicDBObject("expireAfterSeconds", 0));
    }
}
