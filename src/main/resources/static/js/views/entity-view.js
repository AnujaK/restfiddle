define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
    $("#addEntityFieldBtn").unbind("click").bind("click", function() {
        var entityFieldView = new EntityFieldView();
        $("#entityFieldsWrapper").append(entityFieldView.render().el);
	});
    
	var EntityFieldView = Backbone.View.extend({	
        template: _.template($('#tpl-entity-field').html()),
        
		render : function() {
            this.$el.html(this.template());
			return this;
		}
	});
	
	var EntityView = Backbone.View.extend({
		initialize : function() {
		},
		
		render : function() {
            return this;
		}
	});
	
	return EntityView;
});
