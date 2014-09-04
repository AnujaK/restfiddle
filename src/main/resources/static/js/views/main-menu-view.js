define(function(require) {
	
	"use strict";
	
	require('jquery');
	var Workspace = require('models/workspace');
	var ProjectModel = require("models/project");
	var ProjectView = require("views/project-view");
	
	$("#saveProjectBtn").unbind("click").bind("click", function(){
		var project = new ProjectModel({
			name : $("#projectTextField").val(),
			description: $("#projectTextArea").val()
		});
		project.save(null, {
			success : function(response) {
					var projectView = new ProjectView();
					projectView.addOne(project);
					$('#projectModal').modal("hide");
					$("#projectTextField").val("");
					$("#projectTextArea").val("");
				},
			error : function(e) {
				alert('Some unexpected error occured Please try later.');
			}
		});
	});
	
	$("#editProjectBtn").unbind("click").bind("click", function(){
		var project = new ProjectModel({
			id: APP.appView.getCurrentProjectId(),
			name: $("#editProjectTextField").val(),
			description: $("#editProjectTextArea").val()
		});
		project.save(null, {
			success : function(response) {
					$("#editProjectTextField").val("");
					$("#editProjectTextArea").val("");
					location.reload();
				},
			error : function(e) {
				alert('Some unexpected error occured Please try later.');
			}
		});
	});
	

    	$("#createNewCollaboratorBtn").bind("click", function() {
        	$("#collaboratorModal").modal("hide");
        	
        	$.ajax({
        	    url : APP.config.baseUrl + '/users',
        	    type : 'post',
        	    dataType : 'json',
        	    contentType : "application/json",
        	    data : JSON.stringify({
    			name : $("#profileName").val(),
			email : $("#profileEmail").val(),
			password : $("#profilePassword").val()
        	    }),
        	    
        	    success : function() {
        		alert('New collaborator added successfully!');
        	    }
        	});
        });
    	
    	$("#changePasswordBtn").bind("click", function() {
        	$("#changePasswordModal").modal("hide");
        	
        	$.ajax({
        	    url : APP.config.baseUrl + '/users/change-password',
        	    type : 'post',
        	    dataType : 'json',
        	    contentType : "application/json",
        	    data : JSON.stringify({
        		oldPassword : $("#oldPassword").val(),
        		newPassword : $("#newPassword").val(),
        		retypedPassword : $("#retypedPassword").val()
        	    }),
        	    
        	    success : function() {
        		alert('Password changed successfully!');
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