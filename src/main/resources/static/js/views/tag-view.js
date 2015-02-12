define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var TagModel = require('models/tag');
	var TagEvents = require('events/tag-event');

	var TaggedNodeView = require('views/tagged-node-view');
	
	var TagListItemView = Backbone.View.extend({	
		tagName : 'li',
		template : _.template($('#tpl-tag-list-item').html()),
		events : {
			"click a" : "showTaggedNodes",
			"click .hover-down-arrow" : "preventParentElmSelection",
			"hover a"  : "displayDownArrow"
		},
		
		render : function(eventName) {
			$(this.el).html(this.template({
				tag : this.model.toJSON()
			}));
			return this;
		},

		preventParentElmSelection : function(event){
			event.stopPropagation();
			
			var currentElm = $(event.currentTarget);

			if(currentElm.hasClass('open')){
				$('.btn-group').removeClass('open');
				currentElm.removeClass('open');
				this.delegateEvents();
			}else{
				$('.btn-group').removeClass('open');
				currentElm.addClass('open');
				$(this.el).undelegate('a', 'hover');
				
			}
		},

		displayDownArrow : function(event){
		var currentElm = $(event.currentTarget);
			if(event.type == "mouseenter"){
				currentElm.find(".hover-down-arrow").css('visibility','visible')
			}
			if(event.type == "mouseleave"){
				currentElm.find(".hover-down-arrow").css('visibility','hidden')
			}
		},

		showTaggedNodes : function(){
			console.log("Inside showTaggedNodes");
			$('#rf-col-1-body').find('li').each(function(){
				$(this).removeClass('active');
			});
			this.$el.addClass("active");
			$('#starred-items').hide();
			$('#tree').hide();
			$('#history-items').hide();
			$('#tagged-items').show();
			var taggedNodeView = new TaggedNodeView();
			taggedNodeView.showTaggedNodes(this.model.get('id'));
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
		//TODO : Remove me!
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
