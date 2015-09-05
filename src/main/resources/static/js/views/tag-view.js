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
		
		render : function() {
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
			/*console.log("Inside showTaggedNodes");
			$('#rf-col-1-body').find('li').each(function(){
				$(this).removeClass('active');
			});
			this.$el.addClass("active");
			$('#starred-items').hide();
			$('#tree').hide();
			$('#history-items').hide();
			$('#tagged-items').show();
			var taggedNodeView = new TaggedNodeView();
			taggedNodeView.showTaggedNodes(this.model.get('id'));*/
		}
	});

	var TagView = Backbone.View.extend({
	    el : '#rfTags',
	    addOne : function(model){
	    		model.set('workspaceId',APP.appView.getCurrentWorkspaceId());
				var tagListView = new TagListItemView({model: model});
				this.$el.append(tagListView.render().el);
				tagListView.$el.find('a').trigger('click');
				return this;
			},
		initialize : function() {
		 	this.listenTo(this.collection, 'sync', this.render);
		},
			
		render : function(isDefautlView) {
			var activeTag = this.$el.find('.active')[0];
			if(activeTag)
				var activeTagId = $(activeTag).children('a').attr('id');
			
	        this.$el.html('');
			_.each(this.collection.models,function(p, index){
				p.set('workspaceId',APP.appView.getCurrentWorkspaceId());
				var tagListView = new TagListItemView({model: p});
				this.$el.append(tagListView.render().el);
			},this);
			console.log("TagView#render");
			
			if(activeTagId)
				$('#'+activeTagId).parent('li').addClass('active');
		}
	});

return TagView;

});
