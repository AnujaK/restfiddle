define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
    $("#exportRunProjectReport").unbind("click").bind("click", function() {
        window.location = APP.config.baseUrl + '/documentation/projects/1';
    });

	var ProjectRunnerListItemView = Backbone.View.extend({	
		tagName : 'li',
		template : _.template($('#tpl-project-runner-list-item').html()),
		
		render : function(eventName) {
			$(this.el).html(this.template({
				nodeStatusResponse : this.model
			}));
			return this;
		}
	});
	
	var ProjectRunnerView = Backbone.View.extend({
        el: '#projectRunnerSection',
		initialize : function() {
			this.$el.hide();
		},
		
		render : function(projectRunnerModel) {
            this.model = projectRunnerModel;
            var projectRunnerBody = this.$el.find('#projectRunnerBody');
            projectRunnerBody.html('');
            _.each(this.model, function (index) {
                var projectRunnerListItemView = new ProjectRunnerListItemView({model: index});
                projectRunnerBody.append(projectRunnerListItemView.render().el);
            }, this);
            
            return this;
		}
	});
	
	return ProjectRunnerView;
	
});
