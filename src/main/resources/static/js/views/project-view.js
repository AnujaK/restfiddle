define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var tree = require('views/tree-view');
	var ProjectEvents = require('events/project-event');
	
	var ProjectView = Backbone.View.extend({
		el : '#test_project',
		addOne : function(model){
			var projectListView = new ProjectListView({model: model});
			this.$el.append(projectListView.render().el);
			projectListView.$el.find('a').trigger('click');
			return this;
		},
		render : function(isDefautlView){
			this.$el.html('');
			_.each(this.model,function(p, index){
				var projectListView = new ProjectListView({model: p});
				this.$el.append(projectListView.render().el);
				if(index == 0){
					projectListView.$el.find('a').trigger('click');
				}
			},this);
		}
		
	});
	var ProjectListView = Backbone.View.extend({
		tagName : 'li',
		events : {
			"click a" : "showProjectTree"
		},
		template : _.template($('#tpl-project-list-item').html()),
		
		initialize : function() {
			console.log('called initialize');
			//this.render();
		},
		showProjectTree : function(){
			this.$el.parent('ul').find('li').each(function(){
				$(this).removeClass('active');
			})
			this.$el.addClass("active");
			console.log('Project Id : ' + this.$el.find('a').data('project-id'))
			ProjectEvents.triggerChange(this.$el.find('a').data('project-id'));
			console.log('current project id is ' + APP.appView.getCurrentProjectId());
			tree.showTree(this.$el.find('a').data('project-ref-id'));
		},

		render : function() {
			this.$el.html(this.template({project : this.model.toJSON()}));
			return this;
		}
	});
	
	return ProjectView;
});
