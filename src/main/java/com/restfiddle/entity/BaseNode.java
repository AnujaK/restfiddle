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
package com.restfiddle.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class BaseNode extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String nodeType;// PROJECT/FOLDER/ENTITY/SOCKET etc

    private Long parentId;

    private Long position;// location in the parent node

    private Boolean starred;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;

    @OneToOne
    @JsonManagedReference
    private Conversation conversation;

    @OneToOne
    @JsonManagedReference
    private GenericEntity genericEntity;

    @ManyToOne
    @JsonBackReference
    private Project project;

    public String getNodeType() {
	return nodeType;
    }

    public void setNodeType(String nodeType) {
	this.nodeType = nodeType;
    }

    public Long getParentId() {
	return parentId;
    }

    public void setParentId(Long parentId) {
	this.parentId = parentId;
    }

    public Long getPosition() {
	return position;
    }

    public void setPosition(Long position) {
	this.position = position;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public Conversation getConversation() {
	return conversation;
    }

    public void setConversation(Conversation conversation) {
	this.conversation = conversation;
    }

    public Boolean getStarred() {
	return starred;
    }

    public void setStarred(Boolean starred) {
	this.starred = starred;
    }

    public List<Tag> getTags() {
	return tags;
    }

    public void setTags(List<Tag> tags) {
	this.tags = tags;
    }

    public GenericEntity getGenericEntity() {
        return genericEntity;
    }

    public void setGenericEntity(GenericEntity genericEntity) {
        this.genericEntity = genericEntity;
    }

}
