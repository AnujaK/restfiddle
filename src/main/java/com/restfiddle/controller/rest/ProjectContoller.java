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

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.ProjectRepository;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.entity.Project;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ProjectContoller {

    @Resource
    private ProjectRepository projectRepository;

    public Project create(ProjectDTO projectDTO) {
	Project project = new Project();
	// project.setName(name);

	return projectRepository.save(project);
    }

    public Project delete(Long id) {

	return null;
    }

    public List<Project> findAll() {
	return null;
    }

    public Project findById(Long id) {
	return null;
    }

    public Project update(ProjectDTO updated) {
	return null;
    }

}
