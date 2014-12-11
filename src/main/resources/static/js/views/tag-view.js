define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var TagModel = require('models/tag');
	var TagEvents = require('events/tag-event');
	
	var TagListItemView = Backbone.View.extend({	
		tagName : 'li',
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
		
		showTags : function(event){
			APP.tags.fetch({success : function(response){
				$("#rfTags").html('');
				response.each(function(tag) {
					var tagListView = new TagListItemView({
						model : tag
					});
					$("#rfTags").append(tagListView.render().el);
				});
				
			}});			
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
