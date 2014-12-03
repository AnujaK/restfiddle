define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var TagModel = require('models/tag');

	var TagListItemView = Backbone.View.extend({	
		template : _.template($('#tpl-tag-list-item').html()),
		
		render : function(eventName) {
			$(this.el).html(this.template({
				tag : this.model.toJSON()
			}));
			return this;
		}
	});
	
	var TagView = Backbone.View.extend({
		initialize : function() {
			this.listenTo(APP.Events, TagEvents.FETCH, this.handleTags);
		},
		
		handleTags : function(event){
			APP.tags.fetch({success : function(response){
				response.each(function(tag) {
					var tagListView = new TagListItemView({
						model : tag
					});
				});
			}});
		},
		
		render : function(eventName) {
			console.log("TagView#render");
			return this;
		}
	});
	
	return TagView;
	
});
