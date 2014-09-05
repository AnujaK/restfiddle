define(function(require) {

	"use strict";

	require('jquery');
	var Workspace = require('models/workspace');
	var ProjectModel = require("models/project");
	var ProjectView = require("views/project-view");

	$("#saveProjectBtn").unbind("click").bind("click", function() {
		var project = new ProjectModel({
			name : $("#projectTextField").val(),
			description : $("#projectTextArea").val()
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

	$("#editProjectBtn").unbind("click").bind("click", function() {
		var project = new ProjectModel({
			id : APP.appView.getCurrentProjectId(),
			name : $("#editProjectTextField").val(),
			description : $("#editProjectTextArea").val()
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

	$("#editWorkspaceBtn").unbind("click").bind("click", function() {
		var newWorkspace = new Workspace({
			id : APP.appView.getCurrentWorkspaceId(),
			name : $("#editWorkspaceTextField").val(),
			description : $("#editWorkspaceTextArea").val()
		});
		newWorkspace.save(null, {
			success : function(response) {
				$("#editWorkspaceTextField").val("");
				$("#editWorkspaceTextArea").val("");
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
	
	$('#updateProfileModal').on('show.bs.modal', function (e) {
		$.ajax({
			url : APP.config.baseUrl + '/users/current-user',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(data) {
			    $("#updateProfileName").val(data.name);
			    $("#updateProfileEmail").val(data.email);
			}
		});
	});
	
	$("#updateProfileBtn").bind("click", function() {
		$("#updateProfileModal").modal("hide");

		$.ajax({
			url : APP.config.baseUrl + '/users/1',//TODO : user-id is optional
			type : 'put',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify({
			    	name : $("#updateProfileName").val(),
			    	email : $("#updateProfileEmail").val()
			}),

			success : function() {
				alert('Profile updated successfully!');
			}
		});
	});	
	  
	$("#saveWorkspaceBtn").bind("click", saveWorkspace);

	function saveWorkspace() {
		var newWorkspace = new Workspace({
			name : $("#workspaceTextField").val(),
			description : $("#workspaceTextArea").val()
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