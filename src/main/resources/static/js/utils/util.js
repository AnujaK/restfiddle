// TODO : This file must be written properly
define(function(require) {
	require('jquery');
	require('backbone');
	
	$('.col-1-toggle-btn').toggle(function() {
	    $('.rf-col-1').hide();
	    $('.rf-col-2').css('left', '0%');
	    $('.rf-col-3').css('left', '33%');
	    $('.rf-col-3').removeClass('col-xs-6').addClass("col-xs-8");
	
	}, function() {
	    $('.rf-col-1').show();
	    $('.rf-col-2').css('left', '17%');
	    $('.rf-col-3').css('left', '50%');
	    $('.rf-col-3').removeClass('col-xs-8').addClass("col-xs-6");
	});
	
	var onGetProjectsSuccess = function(responseData) {
		var projectList = "";
	    $.each(responseData, function(idx, project) {
				projectList = projectList + '<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'+ project.name + '</a></li>';
		console.log("idx = " + idx + " project = " + project.name);
	    });
		$(".project-list").html(projectList);
	    console.log("projects retrieved successfully!");
	};
	
	var onGetProjectsFailure = function() {
	    console.log("failed");
	    alert("failed");
	};
	//TODO: handling should be under control of some view.
	
	$("#requestBtn").bind("click", function(){
		$("#requestModal").find("#source").val("request");
		$("#requestModal").modal("show");
	});
	
	$("#saveAsConversationBtn").bind("click", function(){
		$("#requestModal").find("#source").val("conversation");
		$("#requestModal").modal("show");
	})
	
	$("#createNewRequestBtn").bind("click",function(){
		var conversation = null
		if($("#requestModal").find("#source").val() == 'request' ){
			conversation = new app.ConversationModel({});
			
		}else if($("#requestModal").find("#source").val() == 'conversation'){
			var rfRequest = {
					apiUrl : $("#apiUrl").val(),
					apiBody : $("#apiBody").val(),
					methodType : $(".apiRequestType").val()
			};
			var rfResponse = {
					
			};
			conversation = new app.ConversationModel({
				id : null,
				rfRequestDTO : rfRequest,
				rfResponseDTO : rfResponse
				
			});
		}else{
			console.log('source is not set properly for modal');
			alert('some error occurs');
		}
		
		app.tree.createNewNode({
			nodeName : $("#requestName").val(),
			conversation : conversation,
			successCallBack : function(){
				$("#requestModal").modal("hide");
			}
		});
		
	});
	
	$("#createNewFolderBtn").bind("click",function(){
		app.tree.createNewNode({
			nodeName : $("#folderId").val(),
			conversation : null,
			successCallBack : function(){
				$("#folderModal").modal("hide");
			}
		});
	});
	
	$("#saveProjectBtn").bind("click", function() {
	    new app.commonService().saveProject(app.appView.getCurrentWorkspaceId(), {
		"name" : $("#projectTextField").val(),
		"description" : $("#projectTextArea").val()
	    }, onSaveProjectSuccess, onSaveProjectFailure);
	});
	var onSaveProjectSuccess = function(responseData) {
		var project = new app.Project(responseData);
		var projectView = new app.ProjectView();
		projectView.addOne(project);
		$('#projectModal').modal("hide");
	/*    $(".project-list").append(
		    '<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'
			    + responseData.name + '</a></li>');
	    console.log("project created successfully!");
	    $('#projectModal').modal("hide");*/
	};
	var onSaveProjectFailure = function() {
	    console.log("failed");
	    alert("failed");
	};
	var bindClickEvent = function(selector,callback){
		$(selector).unbind("click").bind("click",function(event){
			if(callback){
				callback(event);
			}
		});
	};
	var onGetSingleWSSuccess = function(workspace){
		$(".dummyWorkspaceName").val(workspace.name);
	};
	var handleSwitchWS = function(event){
		var wId = $(event.target).attr("id");
		console.log(wId);
		$("#switchWorkspaceModal").modal("hide");
		new app.commonService().getWorkspaces(onGetSingleWSSuccess, onGetWSFail,wId);
	    new app.commonService().getProjects(wId, null, onGetProjectsSuccess, onGetProjectsFailure);
	};
	var onGetWSFail = function(responsdata){
		console.log(responsdata);
		alert("fail");
	};
	/*$(".dummySwitchWorkspace").unbind("click").bind("click", function() {
		$("#switchWorkspaceModal .modal-body").html('<li><a href="#" data-toggle="modal" data-target="#comingSoon">ww2</a></li><li><a href="#" data-toggle="modal" data-target="#comingSoon">w2r</a></li>');
		$("#switchWorkspaceModal").modal("show");
		alert("test it");*/
		/*new app.commonService().getWorkspaces(onGetWSSuccess, onGetWSFail);
		function onGetWSSuccess(responsdata){
			var workSpceList = '<div class="list-group">';
			 $.each(responsdata, function(ids,workSpace) {
				 workSpceList =workSpceList +  '<a href="#" id = '+workSpace.id+' class="dummyWSli list-group-item">'+workSpace.name+'</a>';
			 });
			 workSpceList =workSpceList + '</div>';
			 $("#switchWorkspaceModal .modal-body").html(workSpceList);
			 $("#switchWorkspaceModal").modal("show");
			 bindClickEvent(".dummyWSli",handleSwitchWS);
		};
	});*/

});