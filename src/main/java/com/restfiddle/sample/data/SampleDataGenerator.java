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

import com.restfiddle.controller.rest.ProjectController;
import com.restfiddle.controller.rest.WorkspaceController;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.WorkspaceDTO;

@Component
public class SampleDataGenerator {

    @Autowired
    private WorkspaceController workspaceController;

    @Autowired
    private ProjectController projectController;

    @PostConstruct
    public void initialize() {
	loadWorkspaceData();
	loadProjectData();
    }

    private void loadWorkspaceData() {
	WorkspaceDTO workspaceDTO = new WorkspaceDTO();

	workspaceDTO.setName("Demo Workspace");
	workspaceDTO.setDescription("Demo Workspace ");

	workspaceController.create(workspaceDTO);
    }

    private void loadProjectData() {
	ProjectDTO projectDTO = new ProjectDTO();
	
	projectDTO.setName("My First Project");

	projectController.create(1L, projectDTO);
    }
}
