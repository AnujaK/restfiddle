package com.restfiddle.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.models.Info;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.restfiddle.entity.Project;
import com.restfiddle.util.TreeNode;

public class ExportController {
    Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private ProjectController projectController;

    @Autowired
    private NodeController nodeController;

    @RequestMapping(value = "/api/export/swagger", method = RequestMethod.POST)
    public @ResponseBody
    void exportProjectSwagger(@RequestParam("projectId") String projectId) {
	rfToSwaggerConverter(projectId);
    }

    private void rfToSwaggerConverter(String projectId) {
	Swagger swagger = new SwaggerParser().read("http://petstore.swagger.io/v2/swagger.json");

	Project project = projectController.findById(null, projectId);
	String projectNodeRefId = project.getProjectRef().getId();
	
	TreeNode projectNode = nodeController.getProjectTree(projectNodeRefId, null);
	
	swagger = new Swagger();
	
	//TODO : set host and basepath for swagger
	//swagger.setHost(host);
	//swagger.setBasePath(basePath);
	
	Info info = new Info();
	info.setTitle(projectNode.getName());
	info.setDescription(projectNode.getDescription());
	swagger.setInfo(info);
	
	Map<String, Path> paths = new HashMap<>();
	Path path = null;
	String pathKey = null;
	String method = null;
	Operation op = null;
	String operationId = null;
	String summary = null;
	List<TreeNode> children = projectNode.getChildren();
	for (int i = 0; i < children.size(); i++) {
	    TreeNode childNode = children.get(i);
	    path = new Path();
	    
	    op = new Operation();
	    operationId = childNode.getName();
	    summary = childNode.getDescription();
	    op.setOperationId(operationId);
	    op.setSummary(summary);
	    method = childNode.getMethod().toLowerCase();
	    path.set(method, op);
	    //TODO : pathKey is the relative url (for example /workspaces) from the api url
	    pathKey = null;
	    paths.put(pathKey, path);
	}
	
	swagger.setPaths(paths);

	JsonNode jsonNode = Json.mapper().convertValue(swagger, JsonNode.class);
	System.out.println(jsonNode.toString());
    }
}
