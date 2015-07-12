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
            "click .edit-tag" : "editTag",
            "click .delete-tag" : "deleteTag"
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
			}else{
				$('.btn-group').removeClass('open');
				currentElm.addClass('open');
				var rect = event.currentTarget.getBoundingClientRect();
			    currentElm.children("ul").css({"position": "fixed", "left":rect.left , "top": rect.bottom});
			}
		},
        
        editTag : function(){
            $("#editTagId").val(this.model.get('id'));
            $("#editTagTextField").val(this.model.get('name'));
            $("#editTagTextArea").val(this.model.get('description'));
            $("#editTagModal").modal("show");
        },
        
        deleteTag : function(){
            $("#deleteTagId").val(this.model.get('id'));
            $("#deleteTagModal").modal("show");
        },

		showTaggedNodes : function(){
			console.log("Inside showTaggedNodes");
            window.history.pushState("", "tag", APP.config.root+"tags/"+this.model.get('id'));
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
