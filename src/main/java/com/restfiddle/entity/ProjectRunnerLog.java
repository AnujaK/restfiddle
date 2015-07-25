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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ProjectRunnerLog extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @OneToMany
    private List<ProjectRunnerLogItem> logItems = new ArrayList<ProjectRunnerLogItem>();

    @ManyToOne
    @JsonBackReference
    private Project project;

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public List<ProjectRunnerLogItem> getLogItems() {
	return logItems;
    }

    public void setLogItems(List<ProjectRunnerLogItem> logItems) {
	this.logItems = logItems;
    }

}
