define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	require('selectize');
	
	var ProjectModel = require('models/project');
	var WorkspaceEvents = require('events/workspace-event');
	var ProjectView = require('views/project-view');
	
	$('#workspace-share-tags').selectize({
		persist: false,
		createOnBlur: true,
		create: true
	});
	$('#project-share-tags').selectize({
		persist: false,
		createOnBlur: true,
		create: true
	});
	
	var WorkspaceListView = Backbone.View.extend({
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
				projectList.push(new ProjectModel(p));
			});
			var projectView = new ProjectView({model : projectList});
			projectView.render();
			
			var workspaceNameView = new WorkspaceNameView({model : this.model});
			workspaceNameView.render();
			
			$("#switchWorkspaceModal").modal('hide');
			WorkspaceEvents.triggerChange(this.model.get('id'));
		}
	});

	var WorkspaceNameView = Backbone.View.extend({
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
	
	var WorkspaceView = Backbone.View.extend({
		tagName : 'li',
		el : '#dd-workspace-wrapper',

		template : _.template($('#tpl-workspace-list-item').html()),
		events : {
			"change #dd-workspace" : "handleWorkspaceChange"
		},
		showDefault : function(){
			var view = this;
			APP.workspaces.fetch({success : function(response){
				console.log('fetched wokrspace')
				if(response.get(1)){
					var projects = response.get(1).get('projects');
					var projectList = [];
					_.each(projects, function(p){
						projectList.push(new ProjectModel(p));
					});
					WorkspaceEvents.triggerChange(response.get(1).get('id'));
					var projectView = new ProjectView({model : projectList});
					projectView.render();
					
					var workspaceNameView = new WorkspaceNameView({model : response.get(1)});
					workspaceNameView.render();
				}
			}});
		},
		initialize : function() {
			this.listenTo(APP.Events, WorkspaceEvents.FETCH,
					this.handleWorkspaceChange);
			
		},

		render : function(eventName) {
			
			$(this.el).html(this.template({
				workspace : this.model.toJSON()[0]
			}));
			return this;
		},
		handleWorkspaceChange : function(event) {
			APP.workspaces.fetch({
				success : function(response) {
					$("#switchWorkspaceModal").find('.modal-body').html('')
					response.each(function(workspace) {
						var wsListView = new WorkspaceListView({
							model : workspace
						});
						$("#switchWorkspaceModal").find('.modal-body').append(wsListView.render().el);
					});
					$("#switchWorkspaceModal").modal('show');
				}
			});

		}
	});

	return WorkspaceView;
	
});
