/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};
define(['backbone'], function(Backbone){
	

    var AppView = Backbone.View.extend({
		events : {
	
		},
		workspaceId : '',
		projectId : '',
		initialize : function() {
		    var view = new app.WorkspaceView({
		    	model : app.workspaces
		    });
		    view.showDefault();
		    this.listenTo(app.workspaceEvents, 'change',this.handleWorkspaceChange);
		    this.listenTo(app.projectEvents, 'change',this.handleProjectChange);
		    this.listenTo(app.conversationEvents,'change',this.handleConversationChange);
		    this.render();
		},
		
		handleWorkspaceChange : function(id){
			console.log('workspace changed :' + id);
			app.tree.resetTree();
			this.workspaceId = id;
		},
		handleProjectChange : function(id){
			console.log('project changed :'+ id);
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
