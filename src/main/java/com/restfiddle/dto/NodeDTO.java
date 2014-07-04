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

public class NodeDTO extends BaseDTO {

    private String nodeType;

    private Long parentId;

    private Long position;

    private Long projectId;

    private Boolean starred;
    
    private ConversationDTO conversationDTO;

    public Long getProjectId() {
	return projectId;
    }

    public void setProjectId(Long projectId) {
	this.projectId = projectId;
    }

    public Long getParentId() {
	return parentId;
    }

    public void setParentId(Long parentId) {
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

}
