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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ActivityLog extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String type; // e.g. CONVERSATION

    private String data;

    @ManyToOne
    @JsonBackReference
    private Workspace workspace; //

    @ManyToOne
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JsonBackReference
    private BaseNode baseNode;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    public void setWorkspace(Workspace workspace) {
	this.workspace = workspace;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public BaseNode getBaseNode() {
	return baseNode;
    }

    public void setBaseNode(BaseNode baseNode) {
	this.baseNode = baseNode;
    }
}
