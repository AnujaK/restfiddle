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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.constant.NodeType;
import com.restfiddle.dao.NodeRepository;
import com.restfiddle.dao.ProjectRepository;
import com.restfiddle.dao.WorkspaceRepository;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.Workspace;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Resource
    private WorkspaceRepository workspaceRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private NodeRepository nodeRepository;

    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Project create(@PathVariable("workspaceId") String workspaceId, @RequestBody ProjectDTO projectDTO) {
	logger.debug("Creating a new project with information: " + projectDTO);

	// Create project
	Project project = new Project();
	project.setName(projectDTO.getName());
	project.setDescription(projectDTO.getDescription());

	// Create project reference node
	BaseNode projectRef = new BaseNode();
	projectRef.setName(projectDTO.getName());
	projectRef.setNodeType(NodeType.PROJECT.name());
	projectRef.setParentId("-1");
	projectRef.setPosition(Long.valueOf(0));
	projectRef.setWorkspaceId(workspaceId);

	// Save project reference node
	BaseNode savedRef = nodeRepository.save(projectRef);

	// Set project reference node
	project.setProjectRef(savedRef);

	Project savedProject = projectRepository.save(project);

	// Update projectRef (Set projectId to the reference node)
	projectRef.setProjectId(savedProject.getId());
	nodeRepository.save(projectRef);

	// Update workspace
	Workspace workspace = workspaceRepository.findOne(workspaceId);
	workspace.getProjects().add(savedProject);
	workspaceRepository.save(workspace);

	return savedProject;
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    void delete(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id) {
	logger.debug("Deleting project with id: " + id);

	Project projectToBeDeleted = projectRepository.findOne(id);

	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);

	nodeRepository.delete(listOfNodes);

	projectRepository.delete(projectToBeDeleted);

	Workspace workspace = workspaceRepository.findOne(workspaceId);
	workspace.getProjects().remove(projectToBeDeleted);
	workspaceRepository.save(workspace);
    }

    /*@RequestMapping(value = "/api/workspaces/{workspaceId}/projects", method = RequestMethod.GET)
    public @ResponseBody
    List<Project> findProjectsFromAWorkspace(@PathVariable("workspaceId") String workspaceId) {
	logger.debug("Finding all projects from workspace with id " + workspaceId);

	return projectRepository.findProjectsFromAWorkspace(workspaceId);
    }*/

    public List<Project> findAll() {
	logger.debug("Finding all projects");

	return projectRepository.findAll();
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Project findById(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id) {
	logger.debug("Finding project by id: " + id);

	return projectRepository.findOne(id);
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Project update(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id, @RequestBody ProjectDTO updated) {
	logger.debug("Updating project with information: " + updated);

	Project project = projectRepository.findOne(updated.getId());
	BaseNode projectRef = project.getProjectRef();

	String updatedName = updated.getName();
	if (updatedName != null) {
	    project.setName(updatedName);
	    projectRef.setName(updatedName);
	}

	String updatedDescription = updated.getDescription();
	if (updatedDescription != null) {
	    project.setDescription(updatedDescription);
	    projectRef.setDescription(updatedDescription);
	}

	nodeRepository.save(projectRef);
	projectRepository.save(project);

	return project;
    }

    @RequestMapping(value = "/api/workspaces/{workspaceId}/projects/{id}/copy", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    void copy(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id, @RequestBody NodeDTO nodeDTO) {
	String nodeType = nodeDTO.getNodeType();
	if (!NodeType.PROJECT.name().equalsIgnoreCase(nodeType)) {
	    return;
	}

	BaseNode node = nodeRepository.findOne(id);
	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());
	// TODO : Use NodeController#copyNodesRecursively.
	// copyNodesRecursively(node, node.getParentId());
    }

}
