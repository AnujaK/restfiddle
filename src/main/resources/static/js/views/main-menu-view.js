define(function(require) {
	
	"use strict";
	
	require('jquery');
	var Workspace = require('models/workspace');
	var ProjectModel = require("models/project");
	var ProjectView = require("views/project-view");
	
	$("#saveProjectBtn").unbind("click").bind("click", function(){
		var project = new ProjectModel({
			name : $("#projectTextField").val()
		});
		project.save(null, {
			success : function(response) {
					var projectView = new ProjectView();
					projectView.addOne(project);
					$('#projectModal').modal("hide");			
				},
			error : function(e) {
				alert('Some unexpected error occured Please try later.');
			}
		});
	});

	
	$("#saveWorkspaceBtn").bind("click", saveWorkspace);
	
	function saveWorkspace() {
		var newWorkspace = new Workspace({
			name : $("#workspaceTextField").val(),
		});
		newWorkspace.save(null, {
			success : function() {
				$("#workspaceModal").modal("hide");
			},
			error : function(e) {
				$("#workspaceModal").modal("hide");
				alert('Some unexpected error occured Please try later.');
			}
		});
	}
	
	
});