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
package com.restfiddle.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.WorkspaceRepository;
import com.restfiddle.dto.WorkspaceDTO;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.Workspace;
import com.restfiddle.util.TreeNode;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class WorkspaceController {
    Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

    @Resource
    private ProjectController projectController;
    
    @Resource
    private NodeController nodeController;
    
    @Resource
    private WorkspaceRepository workspaceRepository;

    @RequestMapping(value = "/api/workspaces", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Workspace create(@RequestBody WorkspaceDTO workspaceDTO) {
	logger.debug("Creating a new workspace with information: " + workspaceDTO);

	Workspace workspace = new Workspace();

	workspace.setName(workspaceDTO.getName());
	workspace.setDescription(workspaceDTO.getDescription());

	return workspaceRepository.save(workspace);
    }

    @RequestMapping(value = "/api/workspaces/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    void delete(@PathVariable("id") String id) {
	logger.debug("Deleting workspace with id: " + id);

	Workspace deleted = workspaceRepository.findOne(id);

	List<Project> projects = deleted.getProjects();

	for (Project project : projects) {
	    projectController.delete(id, project.getId());
	}

	workspaceRepository.delete(deleted);
    }

    @RequestMapping(value = "/api/workspaces", method = RequestMethod.GET)
    public @ResponseBody
    List<Workspace> findAll(@RequestParam(value = "name", required = false) String name) {
	logger.debug("Finding all workspaces");
	if (name != null && !name.isEmpty()) {
	    List<Workspace> workspaces = workspaceRepository.findByName(name);
	    return workspaces;
	}
	return workspaceRepository.findAll();
    }

    @RequestMapping(value = "/api/workspaces/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Workspace findById(@PathVariable("id") String id) {
	logger.debug("Finding workspace by id: " + id);

	return workspaceRepository.findOne(id);
    }

    @RequestMapping(value = "/api/workspaces/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Workspace update(@PathVariable("id") String id, @RequestBody WorkspaceDTO updated) {
	logger.debug("Updating workspace with information: " + updated);

	Workspace workspace = workspaceRepository.findOne(updated.getId());

	workspace.setName(updated.getName());
	workspace.setDescription(updated.getDescription());
	
	workspaceRepository.save(workspace);
	
	return workspace;
    }
    
    @RequestMapping(value = "/api/workspaces/{id}/export", method = RequestMethod.GET)
    public @ResponseBody
    List<TreeNode> export(@PathVariable("id") String id) {
	Workspace workspace = workspaceRepository.findOne(id);
	List<Project> projects = workspace.getProjects();
	
	List<TreeNode> projectTreeList = new ArrayList<TreeNode>();
	TreeNode projectTree = null;
	for (Project project : projects) {
	    projectTree = nodeController.getProjectTree(project.getProjectRef().getId());
	    projectTreeList.add(projectTree);
	}
	
	return projectTreeList;
    }

}
