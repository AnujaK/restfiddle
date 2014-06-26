/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
	'use strict';
	app.ProjectView = Backbone.View.extend({
		el : '#test_project',
		addOne : function(model){
			var projectListView = new app.ProjectListView({model: model});
			this.$el.append(projectListView.render().el);
			return this;
		},
		render : function(isDefautlView){
			this.$el.html('');
			_.each(this.model,function(p, index){
				var projectListView = new app.ProjectListView({model: p});
				this.$el.append(projectListView.render().el);
				if(index == 0){
					projectListView.$el.find('a').trigger('click');
				}
			},this);
		}
		
	})
	app.ProjectListView = Backbone.View.extend({
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
			app.projectEvents.triggerChange(this.$el.find('a').data('project-id'));
			console.log('current project id is ' + app.appView.getCurrentProjectId())
			app.tree.showTree(app.appView.getCurrentProjectId());
			//console.log('will fetch project tree');
		},

		render : function() {
			this.$el.html(this.template({project : this.model.toJSON()}));
			return this;
		}
	});
})(jQuery);
