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
package com.restfiddle.dto;

import java.util.List;

public class NodeDTO extends BaseDTO {

    private String nodeType;

    private String parentId;

    private Long position;

    private String projectId;

    private Boolean starred;

  //API method type - GET/POST/PUT/DELETE etc.
    private String method;
    
    private ConversationDTO conversationDTO;

    private GenericEntityDTO genericEntityDTO;

    private List<TagDTO> tags;
    
    private String apiURL;
    
    private String workspaceId;

    public String getProjectId() {
	return projectId;
    }

    public void setProjectId(String projectId) {
	this.projectId = projectId;
    }

    public String getParentId() {
	return parentId;
    }

    public void setParentId(String parentId) {
	this.parentId = parentId;
    }

    public ConversationDTO getConversationDTO() {
	return conversationDTO;
    }

    public void setConversationDTO(ConversationDTO conversationDTO) {
	this.conversationDTO = conversationDTO;
    }

    public String getNodeType() {
	return nodeType;
    }

    public void setNodeType(String nodeType) {
	this.nodeType = nodeType;
    }

    public Long getPosition() {
	return position;
    }

    public void setPosition(Long position) {
	this.position = position;
    }

    public Boolean getStarred() {
	return starred;
    }

    public void setStarred(Boolean starred) {
	this.starred = starred;
    }

    public List<TagDTO> getTags() {
	return tags;
    }

    public void setTags(List<TagDTO> tags) {
	this.tags = tags;
    }

    public GenericEntityDTO getGenericEntityDTO() {
	return genericEntityDTO;
    }

    public void setGenericEntityDTO(GenericEntityDTO genericEntityDTO) {
	this.genericEntityDTO = genericEntityDTO;
    }

    public String getMethod() {
	return method;
    }

    public void setMethod(String method) {
	this.method = method;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

}
