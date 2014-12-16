define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	
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
			
		},
		
		render : function(projectRunnerModel) {
            this.model = projectRunnerModel;
            this.$el.html('');
            _.each(this.model, function (index) {
                var projectRunnerListItemView = new ProjectRunnerListItemView({model: index});
                this.$el.append(projectRunnerListItemView.render().el);
            }, this);
            
            return this;
		}
	});
	
	return ProjectRunnerView;
	
});
