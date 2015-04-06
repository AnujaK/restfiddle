define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var APP = require('commons/ns');
	
	var MenuView = require("views/main-menu-view");
	var UserView = require('views/user-view');
	var TagView = require('views/tag-view');
	var TagsView = require('views/tags-view');
	var WorkspaceView = require('views/workspace-view');
	var StarView = require('views/star-view');
	var ConversationView = require('views/conversation-view');
    var ProjectRunnerView = require('views/project-runner-view');
    var SocketConnectorView = require('views/socket-connector-view');
    var EntityView = require('views/entity-view');
    var ManageEnvironmentView = require('views/environment-view');
    var HistoryView = require('views/history-view');
	var tree = require('views/tree-view');
	
	var WorkspaceEvents = require('events/workspace-event');
	var ProjectEvents = require('events/project-event');
	var ConversationEvents = require('events/conversation-event');
	var UserEvents = require('events/user-event');	
	var TagEvents = require('events/tag-event');
	var StarEvent = require('events/star-event');
	
	var UserCollection = require('collections/users');
	var TagCollection = require('collections/tags');
	var EnvironmentCollection = require('collections/environments');
	var WorkspaceCollection = require('collections/workspaces'); //TODO : REMOVE FROM HERE

    var AppView = Backbone.View.extend({
		events : {
	
		},
		workspaceId : '',
		projectId : '',
		requestId : '',
		initialize : function() {
			
			APP.Events = _.extend({}, Backbone.Events);
			
			APP.users = new UserCollection();
			var userView = new UserView({
				model : APP.users
			});
			
			APP.tags = new TagCollection();
			var tagView = new TagView({
				model : APP.tags
			});
			
			APP.conversation = new ConversationView();
			APP.tagsLabel = new TagsView();
            APP.projectRunner = new ProjectRunnerView();
            APP.socketConnector = new SocketConnectorView();
            
            APP.historyView = new HistoryView();
			
			APP.workspaces = new WorkspaceCollection(); //TODO: REMOVE FROM HERE
			APP.environments = new EnvironmentCollection();
		    var workspaceView = new WorkspaceView({
		    	model : APP.workspaces
		    });
		    
			var starView = new StarView({
				model : APP.node
			});
		   
			workspaceView.showDefault();
		    this.listenTo(APP.Events, WorkspaceEvents.CHANGE,this.handleWorkspaceChange);
		    this.listenTo(APP.Events, ProjectEvents.CHANGE ,this.handleProjectChange);
		    this.listenTo(APP.Events,ConversationEvents.CHANGE,this.handleConversationChange);
			this.listenTo(APP.Events, StarEvent.CLICK,this.setRequestNodeId);
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
					$("#editWorkspaceId").val(workspace.id);
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
		setRequestNodeId : function(id){
			this.requestId = id;
		},
		getCurrentRequestNodeId : function(){
			return this.requestId;
		},
		render : function() {
		    console.log("app-view#render");
		}
    });
    return AppView;  
});
