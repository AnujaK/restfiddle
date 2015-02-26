define(function(require) {

	"use strict";

	require('jquery');
	require('mCustomScrollbar');
	require("libs/jquery.validate");
	var Workspace = require('models/workspace');
	var ProjectModel = require("models/project");
	var TagModel = require("models/tag");
	var NodeModel = require("models/node");
	var ProjectView = require("views/project-view");
	var StarView = require("views/star-view");
	var _ = require('underscore');

	$(".run-project").unbind("click").bind("click", function() {
		APP.conversation.$el.hide();
		APP.socketConnector.$el.hide();
		APP.projectRunner.$el.show();
		var projectId = APP.appView.getCurrentProjectId();
		$.ajax({
			url : APP.config.baseUrl + '/processor/projects/'+projectId,
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(response) {
				console.log("project runner response : "+response);
				APP.projectRunner.render(response);
			}
		});
	});

	$(".socket-connector").unbind("click").bind("click", function() {
		APP.conversation.$el.hide();
		APP.projectRunner.$el.hide();
		APP.socketConnector.$el.show();
	});
	
	$(".starred").unbind("click").bind("click", function() {
		$('#rf-col-1-body').find('li').each(function(){
			$(this).removeClass('active');
		});
		$(this).addClass('active');

		$.ajax({
			url : APP.config.baseUrl + '/nodes/starred',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(response) {
				var starView = new StarView({model:response});
				starView.render();
			}
		});
		
		$('#tree').hide();
		$('#tagged-items').hide();
		$('#history-items').hide();
		$('#starred-items').show();
	});

	$(".history").unbind("click").bind("click", function() {
		$('#rf-col-1-body').find('li').each(function(){
			$(this).removeClass('active');
		});
		$(this).addClass('active');

		$.ajax({
			url : APP.config.baseUrl + '/conversations',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(response) {
				APP.historyView.render(response);
			}
		});
		
		$('#tree').hide();
		$('#tagged-items').hide();
		$('#starred-items').hide();
		$('#history-items').show();
	});

	$('#projectCreationForm').validate({
		messages : {
			projectName : "Project name is empty"
		}
	});

	$("#projectModal").on('show.bs.modal',function(e){
		$("#project-error").text("");
		$("#projectTextField").val("");
		$("#projectTextArea").val("");
	});
	
	$("#saveProjectBtn").unbind("click").bind("click", function() {
		if($("#projectCreationForm").valid()){
			APP.workspaces.fetch({
				success : function(response){
					var currenctWorkspace = _.findWhere(response.models,{id : APP.appView.getCurrentWorkspaceId()});
					var projects = currenctWorkspace.get('projects');
					var projectWithSameName = _.findWhere(projects,{name : $("#projectTextField").val()});
					if(!projectWithSameName){
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
					}else{
						$("#project-error").text("Project name already exist");
					}
				}
			})
		}
	});

$("#tagModal").on('show.bs.modal',function(e){
	$("#tag-name-error").text("");
	$("#tagTextField").val("");
	$("#tagTextArea").val("");
});

$('#tagForm').validate({
	messages : {
		tagName : "Tag name is empty"
	}
});

$("#saveTagBtn").unbind("click").bind("click", function() {
	if($("#tagForm").valid()){
		var that = this;
		APP.tags.fetch({
			success : function(response){
				that.collection = response;
				var tagWithSameName = that.collection.findWhere({name : $("#tagTextField").val()});
				if(!tagWithSameName){
					var tag = new TagModel({
						name : $("#tagTextField").val(),
						description : $("#tagTextArea").val()
					});
					tag.save(null, {
						success : function(response) {
							$('#tagModal').modal("hide");
							$("#tagTextField").val("");
							$("#tagTextArea").val("");
							location.reload();
						},
						error : function(e) {
							alert('Some unexpected error occured Please try later.');
						}
					});
				}
				else{
					$("#tag-name-error").text("Tag name already exist");
				}
			}
		})
	}
});	

$("#editProjectBtn").unbind("click").bind("click", function() {
	var project = new ProjectModel({
		id : $("#editProjectId").val(),
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

$("#editTagBtn").unbind("click").bind("click", function() {
	var tag = new TagModel({
		id : $("#editTagId").val(),
		name : $("#editTagTextField").val(),
		description : $("#editTagTextArea").val()
	});
	tag.save(null, {
		success : function(response) {
			$("#editTagTextField").val("");
			$("#editTagTextArea").val("");
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

$('#workspaceForm').validate({
	messages : {
		workspaceName : "Workspace name is empty"
	}
});


$("#workspaceModal").on('show.bs.modal',function(e){
	$("#workspace-error").text("");
	$("#workspaceTextField").val("");
	$("#workspaceTextArea").val("");
});

function saveWorkspace() {

	if($("#workspaceForm").valid()){
		var that = this;

		APP.workspaces.fetch({
			success : function(response){
				that.collection = response;
				var modelWithSameName = that.collection.findWhere({name : $("#workspaceTextField").val()});
				if(!modelWithSameName){
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
				}else{
					$("#workspace-error").text("Workspace name already exist");
				}
			}
		});

	}
};


});