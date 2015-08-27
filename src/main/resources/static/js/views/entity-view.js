define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
    $("#addEntityFieldBtn").unbind("click").bind("click", function() {
        var entityFieldView = new EntityFieldView();
        $("#entityFieldsWrapper").append(entityFieldView.render().el);
	});
    
    $("#addFieldEditEntityBtn").unbind("click").bind("click", function() {
        var entityFieldView = new EntityFieldView();
        $("#editEntityFieldsWrapper").append(entityFieldView.render().el);
        $("#regenerateAPI").prop("disabled",false);
	});
    
	var EntityFieldView = Backbone.View.extend({	
        template: _.template($('#tpl-entity-field').html()),
        
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
	
	var EntityView = Backbone.View.extend({
		initialize : function() {
		},
		
		render : function() {
            return this;
		}
	});
	
	return EntityView;
});
