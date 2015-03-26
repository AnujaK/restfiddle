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
	$("#projectCreationForm").submit(function(e) {
		e.preventDefault();
	});

	$('#projectTextField').keyup(function() {
	    if($('#projectTextField').val() == ''){
	    	$('#project-error').remove();
	    };
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
						$('#projectTextField').after('<label class="text-danger" id="project-error">Project name already exists</label>');
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

$("#tagForm").submit(function(e) {
	e.preventDefault();
});

$('#tagTextField').keyup(function() {
    if($('#tagTextField').val() == ''){
    	$('#tag-name-error').remove();
    };
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
					$('#tagTextField').after('<label class="text-danger" id="tag-name-error">Tag name already exists</label>');
				}
			}
		})
	}
});	

$("#editProjectModal").on('show.bs.modal',function(e){
	$("#project-edit-error").text("");
});

$('#projectEditForm').validate({
	messages : {
		projectName : "Project name is empty"
	}
});

$('#editProjectTextField').keyup(function() {
    if($('#editProjectTextField').val() == ''){
    	$('#project-edit-error').remove();
    };
});

$("#projectEditForm").submit(function(e) {
	e.preventDefault();
});

$("#editProjectBtn").unbind("click").bind("click", function() {
	if($("#projectEditForm").valid()){
		APP.workspaces.fetch({
			success : function(response){
				var currenctWorkspace = _.findWhere(response.models,{id : APP.appView.getCurrentWorkspaceId()});
				var projects = currenctWorkspace.get('projects');
				var projectWithSameName = _.findWhere(projects,{name : $("#editProjectTextField").val()});
				
				var saveProject = function(){
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
				};

				if(!projectWithSameName){
					saveProject();
				}else{

					if(projectWithSameName.id == $('#editProjectId').val()){
						saveProject();
					}else{
						$('#editProjectTextField').after('<label class="text-danger" id="project-edit-error">Project name already exists</label>');
					}
				}
			}
		})
}
});

$("#editTagModal").on('show.bs.modal',function(e){
	$("#tag-name-edit-error").text("");
});

$('#tagEditForm').validate({
	messages : {
		tagName : "Tag name is empty"
	}
});

$('#editTagTextField').keyup(function() {
    if($('#editTagTextField').val() == ''){
    	$('#tag-name-edit-error').remove();
    };
});

$("#tagEditForm").submit(function(e) {
	e.preventDefault();
});

$("#editTagBtn").unbind("click").bind("click", function() {

	if($("#tagEditForm").valid()){
		var that = this;
		APP.tags.fetch({
			success : function(response){
				that.collection = response;
				var tagWithSameName = that.collection.findWhere({name : $("#editTagTextField").val()});
				var saveTag = function(){
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
				}
				if(!tagWithSameName){
					saveTag();
				}
				else{
					if(tagWithSameName.id == $("#editTagId").val()){
						saveTag();
					}else{
						$('#editTagTextField').after('<label class="text-danger" id="tag-name-edit-error">Tag name already exists</label>');
					}
					
				}
			}
		})
	}
	
});

$("#editWorkspaceModal").on('show.bs.modal',function(e){
	$("#workspace-edit-error").text("");
});

$('#workspaceEditForm').validate({
	messages : {
		workspaceName : "Workspace name is empty"
	}
});

$('#editWorkspaceTextField').keyup(function() {
	    if($('#editWorkspaceTextField').val() == ''){
	    	$('#workspace-edit-error').remove();
	    };
});

$("#workspaceEditForm").submit(function(e) {
	e.preventDefault();
});

$("#editWorkspaceBtn").unbind("click").bind("click", function() {
	if($("#workspaceEditForm").valid()){
		var that = this;

		var saveWorkspace = function(){
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
		};

		APP.workspaces.fetch({
			success : function(response){
				that.collection = response;
				var modelWithSameName = that.collection.findWhere({name : $("#editWorkspaceTextField").val()});
				if(!modelWithSameName){
					saveWorkspace();
				}else{
					if(modelWithSameName == $("#editWorkspaceId").val()){
						saveWorkspace();
					}else{
						$('#editWorkspaceTextField').after('<label class="text-danger" id="workspace-edit-error">Workspace name already exists</label>');
					}
					
				}
			}
		});

	}
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

$("#workspaceForm").submit(function(e) {
	e.preventDefault();
});

$('#workspaceTextField').keyup(function() {
	    if($('#workspaceTextField').val() == ''){
	    	$('#workspace-error').remove();
	    };
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
					$('#workspaceTextField').after('<label class="text-danger" id="workspace-error">Workspace name already exists</label>');
				}
			}
		});

	}
};


});