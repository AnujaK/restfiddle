/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};


define(function(require) {
	
	require('backbone');
	require('underscore');
	
	app.WorkspaceListView = Backbone.View.extend({
		events : {
			"click .dummyWSli" : "showProjects"
		},
		template : _.template($("#tpl-workspace-all-list-item").html()),
		render : function() {
			this.$el.html(this.template({
				workspace : this.model.toJSON()
			}));
			return this;
		},
		showProjects : function() {
		
			var projectList = []
			_.each(this.model.get('projects'), function(p){
				projectList.push(new app.Project(p))
			})
			var projectView = new app.ProjectView({model : projectList});
			projectView.render();
			
			var workspaceNameView = new app.WorkspaceNameView({model : this.model});
			workspaceNameView.render();
			
			$("#switchWorkspaceModal").modal('hide');
			app.workspaceEvents.triggerChange(this.model.get('id'));
		}
	});

	app.WorkspaceNameView = Backbone.View.extend({
		tagName : 'li',
		el : '#dd-workspace-wrapper',
		template : _.template($('#tpl-workspace-list-item').html()),
		render : function(eventName) {
			$(this.el).html(this.template({
				workspace : this.model.toJSON()
			}));
			return this;
		}
	});
	
	app.WorkspaceView = Backbone.View.extend({
		tagName : 'li',
		el : '#dd-workspace-wrapper',

		template : _.template($('#tpl-workspace-list-item').html()),
		services : new app.commonService(),
		events : {
			"change #dd-workspace" : "handleWorkspaceChange"
		},
		bindEvent : function(selector, callback) {
			var self = this;
			$(selector).unbind("click").click(function() {
				if (callback) {
					callback();
				}
			});
		},
		showDefault : function(){
			var view = this;
			app.workspaces.fetch({success : function(response){
				if(response.get(1)){
					var projects = response.get(1).get('projects');
					var projectList = [];
					_.each(projects, function(p){
						projectList.push(new app.Project(p))
					});
					app.workspaceEvents.triggerChange(response.get(1).get('id'));
					var projectView = new app.ProjectView({model : projectList});
					projectView.render();
					
					var workspaceNameView = new app.WorkspaceNameView({model : response.get(1)});
					workspaceNameView.render();
				}
			}});
		},
		initialize : function() {
			_(this).bindAll('saveWorkspace');
			//this.listenTo(app.workspaces, 'sync', this.render);
			this.listenTo(app.workspaceEvents, 'fetch',
					this.handleWorkspaceChange);
			
		},

		render : function(eventName) {
			var self = this;
			// console.log(eventName + ">>>" +
			// JSON.stringify(this.model.toJSON()));
			$(this.el).html(this.template({
				workspace : this.model.toJSON()[0]
			}));
			self.bindEvent("#saveWorkspaceBtn", self.saveWorkspace);
			return this;
		},
		saveWorkspace : function() {
			var wkView = this;
			var newModel = new app.Workspace();
			newModel.set({
				"name" : $("#workspaceTextField").val(),
				"description" : $("#workspaceTextArea").val()
			});
			wkView.services.saveWorkspace(newModel.toJSON(),
					wkView.onSaveWorkspceSuc, wkView.onSaveWorkspceSuc);
			wkView.model.add(newModel);
			wkView.render();
		},
		onSaveWorkspceSuc : function(responseData) {
			$('#workspaceModal').modal("hide");
			console.log("success");
		},
		onSaveWorkspceFail : function() {
			console.log("failed");
			alert("failed");
		},
		clear : function() {
			this.model.destroy();
		},
		handleWorkspaceChange : function(event) {
			app.workspaces.fetch({
				success : function(response) {
					$("#switchWorkspaceModal").find('.modal-body').html('')
					response.each(function(workspace) {
						var wsListView = new app.WorkspaceListView({
							model : workspace
						});
						$("#switchWorkspaceModal").find('.modal-body').append(wsListView.render().el);
					});
					$("#switchWorkspaceModal").modal('show');
				}
			});

		}
	});
});
