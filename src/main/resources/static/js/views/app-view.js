define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var APP = require('commons/ns');
	
	var MenuView = require("views/main-menu-view");
	var UserView = require('views/user-view');
	var TagView = require('views/tag-view');
	var WorkspaceView = require('views/workspace-view');
	var ConversationView = require('views/conversation-view');
	var tree = require('views/tree-view');
	
	var WorkspaceEvents = require('events/workspace-event');
	var ProjectEvents = require('events/project-event');
	var ConversationEvents = require('events/conversation-event');
	var UserEvents = require('events/user-event');	
	var TagEvents = require('events/tag-event');
	
	var UserCollection = require('collections/users');
	var WorkspaceCollection = require('collections/workspaces'); //TODO : REMOVE FROM HERE

    var AppView = Backbone.View.extend({
		events : {
	
		},
		workspaceId : '',
		projectId : '',
		initialize : function() {
			
			APP.Events = _.extend({}, Backbone.Events);
			
			APP.users = new UserCollection();
			var userView = new UserView({
				model : APP.users
			});
			
			
			APP.conversation = new ConversationView();
			
			APP.workspaces = new WorkspaceCollection(); //TODO: REMOVE FROM HERE
		    var view = new WorkspaceView({
		    	model : APP.workspaces
		    });
		   
		    view.showDefault();
		    this.listenTo(APP.Events, WorkspaceEvents.CHANGE,this.handleWorkspaceChange);
		    this.listenTo(APP.Events, ProjectEvents.CHANGE ,this.handleProjectChange);
		    this.listenTo(APP.Events,ConversationEvents.CHANGE,this.handleConversationChange);
		    this.render();
		},
		
		handleWorkspaceChange : function(id){
			console.log('workspace changed :' + id);
			
			$.ajax({
				url : APP.config.baseUrl + '/workspaces/' + id,
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(workspace) {
					$("#editWorkspaceTextField").val(workspace.name);
					$("#editWorkspaceTextArea").val(workspace.description);
				}
			});
			
			tree.resetTree();
			this.workspaceId = id;
		},
		handleProjectChange : function(id){
			console.log('project changed :'+ id);
			
			$.ajax({
				url : APP.config.baseUrl + '/workspaces/' + this.workspaceId + '/projects/' + id,
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(project) {
					$("#editProjectTextField").val(project.name);
					$("#editProjectTextArea").val(project.description);
				}
			});
			
			this.projectId = id;
		},
		handleConversationChange : function(id){
			console.log('conversation changed :'+ id);
			this.conversationId = id;
		},
		getCurrentWorkspaceId : function(){
			return this.workspaceId;
		},
		getCurrentProjectId : function(){
			return this.projectId;
		},
		getCurrentConversationId : function(){
			return this.conversationId;
		},
		render : function() {
		    console.log("app-view#render");
		}
    });
    return AppView;  
});
