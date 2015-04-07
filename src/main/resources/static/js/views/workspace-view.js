define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	require('selectize');
	
	var WorkspaceEvents = require('events/workspace-event');
	
	var ProjectModel = require('models/project');
	//var TagModel = require('models/tag');
	
	var ProjectView = require('views/project-view');
	var ManageEnvironmentView = require('views/environment-view');
	var UserView = require('views/user-view');
	var TagView = require('views/tag-view');
	var TagsView = require('views/tags-view');
	
	//Initialize all tooltips.
	$('[data-toggle="tooltip"]').tooltip();
	
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
			
            if(APP.workspaceNameView != undefined){
                APP.workspaceNameView.undelegateEvents();
            }
            
			APP.workspaceNameView = new WorkspaceNameView({model : this.model});
			APP.workspaceNameView.render();
			
			$("#switchWorkspaceModal").modal('hide');
			WorkspaceEvents.triggerChange(this.model.get('id'));
		}
	});

	var WorkspaceNameView = Backbone.View.extend({
		tagName : 'li',
		el : '#dd-workspace-wrapper',
		template : _.template($('#tpl-workspace-list-item').html()),
		events : {
			"click .hover-down-arrow" : "preventParentElmSelection"
		},
        
		render : function(eventName) {
			$(this.el).html(this.template({
				workspace : this.model.toJSON()
			}));
			return this;
		},
		preventParentElmSelection : function(event){
			event.stopPropagation();
			
			var currentElm = $(event.currentTarget);

			if(currentElm.hasClass('open')){
				$('.btn-group').removeClass('open');
				currentElm.removeClass('open');
			}else{
				$('.btn-group').removeClass('open');
				currentElm.addClass('open');

			}
			
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
			var userView = new UserView();
			userView.showUsers();
			
			var tagView = new TagView();
			tagView.showTags();

			var tagsView = new TagsView();
			tagsView.showTags();

			var environmentView   = new ManageEnvironmentView();
			environmentView.render();
			
			APP.workspaces.fetch({success : function(response){
				console.log('fetched wokrspace');
				if(response.at(0)){
					var projects = response.at(0).get('projects');
					var projectList = [];
					_.each(projects, function(p){
						projectList.push(new ProjectModel(p));
					});
					WorkspaceEvents.triggerChange(response.at(0).get('id'));
					var projectView = new ProjectView({model : projectList});
					projectView.render();
					
					APP.workspaceNameView = new WorkspaceNameView({model : response.at(0)});
					APP.workspaceNameView.render();
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

		},
		displayWorkspaceName : function(workspace){
			APP.workspaceNameView = new WorkspaceNameView({model : workspace});
			APP.workspaceNameView.render();
			
		}
	});

	return WorkspaceView;
	
});
