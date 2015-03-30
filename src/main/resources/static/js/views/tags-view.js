define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var TagModel = require('models/tag');
	var TagEvents = require('events/tag-event');
	var TreeView = require('views/tree-view');
	var NodeModel = require('models/node');
	
	var TagsListItemView = Backbone.View.extend({	
		tagName : 'li',
		template : _.template($('#tpl-tags-list-item').html()),
		events : {
			"click input" : "displayLabel"
		},
		
		render : function(eventName) {
			$(this.el).html(this.template({
				tag : this.model.toJSON()
			}));
			return this;
		},
		displayLabel : function(event){
			var $elm = $(event.currentTarget);
			if($elm.attr("checked")){
				$("#" + $elm.attr('id') + "Label").show();
			}else{
				$("#" + $elm.attr('id') + "Label").hide();
			}
			APP.Events.trigger(TagEvents.SAVE);
		}

	});

    var TagsView = Backbone.View.extend({
        initialize: function () {
          this.listenTo(APP.Events, TagEvents.SAVE, this.addTags);
    	},

    	addTags : function(){
           if (APP.appView.getCurrentRequestNodeId() != null) {
                console.log("conversation id is ..." + APP.appView.getCurrentRequestNodeId());
	            var tags = [];
	            APP.tags.fetch({
			    success : function(response){
					response.each(function(tag) {
						if($("#" + tag.get('name')).attr("checked") == 'checked'){
							tags.push({"id" : tag.get("id")});
						}
					});
				$.ajax({
					url : APP.config.baseUrl + '/nodes/' + APP.appView.getCurrentRequestNodeId() + '/tags',
					type : 'post',
					dataType : 'json',
                    contentType : "application/json",
					data : JSON.stringify(tags),
					success : function(response) {
						console.log("Import file response : "+response);
					}
				});
			       }
			    });	
	         
                		
            }
    	},
		showTags : function(event){
			APP.tags.fetch({
			    success : function(response){
				
					response.each(function(tag) {
						var tagsListView = new TagsListItemView({
							model : tag
						});
						$(".label-dropdown-menu").append(tagsListView.render().el);
						$("#tagLabels").append('<span class="label label-default" id ="'+ tag.get('name')+'Label">'+tag.get('name')+'</span>&nbsp;&nbsp;');
						$("#" + tag.get('name')+ "Label").hide();
					});

			    }
			});			
		},

		display : function(tags){
			var that  = this;
			APP.tags.fetch({
			    success : function(response){
			    	response.each(function(tag) {
                           $("#" + tag.get('name')).attr("checked",false);
                           $("#" + tag.get('name') + "Label").hide();
					});
					response.each(function(tag) {
						var selectedTag = _.findWhere(tags, {name: tag.get('name')});
						if(selectedTag){
                           $("#" + selectedTag.name).attr("checked",true);
                           $("#" + selectedTag.name + "Label").show();
					    }
					});
			    }
			});			
			
			
		},
		render : function(eventName) {
			console.log("TagsView#render");
			return this;
		}
	});

    return TagsView;

});
