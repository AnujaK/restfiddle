define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	
	var TaggedNodeListItemView = Backbone.View.extend({	
		tagName : 'li',
		
		render : function(eventName) {
			return this;
		}
	});
	
	var TaggedNodeView = Backbone.View.extend({
		initialize : function() {
			
		},
		
		render : function(eventName) {
			return this;
		}
	});
	
	return TaggedNodeView;
	
});
