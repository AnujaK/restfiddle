define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
    $("#addNewEnvironmentBtn").unbind("click").bind("click", function() {
        var environmentView = new EnvironmentView();
        $("#environmentWrapper").html("");
        $("#environmentWrapper").append(environmentView.render().el);
	});

	var EnvFieldView = Backbone.View.extend({	
        template: _.template($('#tpl-env-field').html()),
        
        events : {
            'click .destroy': 'clear',
        },
        
		render : function() {
            this.$el.html(this.template());
			return this;
		},
        
        clear : function(){
            this.remove();
        }
	});
	
	var EnvironmentView = Backbone.View.extend({
        template: _.template($('#tpl-environment').html()),
        
        events : {
            'click #addEnvFieldBtn': 'addEnvProperty',
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            return this;
		},
        
        addEnvProperty : function(){
            var envFieldView = new EnvFieldView();
            this.$el.find("#envFieldsWrapper").append(envFieldView.render().el);            
        }
	});
	
	return EnvironmentView;
});
