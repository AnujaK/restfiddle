define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');	
    
    $("#manageAsserts").unbind("click").bind("click", function() {
        var assertListView = new AssertListView();
        $("#manageAssertsWrapper").html("");
        $("#manageAssertsWrapper").append(assertListView.render().el); 
	});
    
	var AssertView = Backbone.View.extend({	
        template: _.template($('#tpl-assert').html()),
        
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
	
	var AssertListView = Backbone.View.extend({
        template: _.template($('#tpl-manage-asserts').html()),
        
        events : {
            'click #addAssertBtn': 'addAssert',
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            return this;
		},
        
        addAssert : function(){
            var assertView = new AssertView();
            this.$el.find("#assertsWrapper").append(assertView.render().el);            
        }
	});
    
	return AssertListView;
});
