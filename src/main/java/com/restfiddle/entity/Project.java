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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Project extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JsonBackReference
    private Workspace workspace;

    @OneToOne
    @JsonManagedReference
    private BaseNode projectRef;

    public Workspace getWorkspace() {
	return workspace;
    }

    public void setWorkspace(Workspace workspace) {
	this.workspace = workspace;
    }

    public BaseNode getProjectRef() {
	return projectRef;
    }

    public void setProjectRef(BaseNode projectRef) {
	this.projectRef = projectRef;
    }
}
