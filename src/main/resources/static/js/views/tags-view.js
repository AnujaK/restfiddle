define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var TagModel = require('models/tag');
	var TagEvents = require('events/tag-event');
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
			if($elm.is(":checked")){
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
        addOne : function(tag){
			var tagsListView = new TagsListItemView({model: tag});    
            $(".label-dropdown-menu").append(tagsListView.render().el);
            $("#tagLabels").append('<span class="label label-default" id ="'+ tag.get('name')+'Label">'+tag.get('name')+'</span>&nbsp;&nbsp;');
            $("#" + tag.get('name')+ "Label").hide();
			return this;
		},
    	addTags : function(){
           if (APP.appView.getCurrentRequestNodeId() != null) {
                console.log("conversation id is ..." + APP.appView.getCurrentRequestNodeId());
                var nodeId;
                if($('#tree').css('display') == "none"){
                	nodeId = $("#currentStaredNode").val();
                }else{
                	nodeId = APP.appView.getCurrentRequestNodeId();
                }
	            var tags = [];
	            APP.tags.fetch({
			    success : function(response){
					response.each(function(tag) {
						if($("#" + tag.get('name')).is(':checked')){
							tags.push({"id" : tag.get("id")});
						}
					});
                    $.ajax({
                        url : APP.config.baseUrl + '/nodes/' + nodeId + '/tags',
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

		display : function(tags){
			var that  = this;
			_.each(tags,function(item,index){
				if(!item){
					tags.splice(index,1);
				}
			})

			APP.tags.fetch({
			    success : function(response){
			    	that.model = response.models;
			    	that.render();
			    	response.each(function(tag) {
                           $("#" + tag.get('name')).prop("checked",false);
                           $("#" + tag.get('name') + "Label").hide();
					});
					response.each(function(tag) {
						var selectedTag = _.findWhere(tags, {name: tag.get('name')});
						if(selectedTag){
                           $("#" + selectedTag.name).prop("checked",true);
                           $("#" + selectedTag.name + "Label").show();
					    }
					});
			    }
			});			
			
			
		},
		render : function(isDefaultView) {
            this.$el.html('');
            $("#tagLabels").empty();
			$(".label-dropdown-menu").empty();
			$(".label-dropdown-menu").append('<li>Select Tags</li>');
			_.each(this.model,function(tag, index){
				var tagsListView = new TagsListItemView({model: tag});
				this.$el.append(tagsListView.render().el);
                $(".label-dropdown-menu").append(tagsListView.render().el);
				$("#tagLabels").append('<span class="label label-default" id ="' +tag.get('name')+'Label">'+tag.get('name')+'</span>&nbsp;&nbsp;');
				$("#" + tag.get('name')+ "Label").hide();
			},this);
		}
	});

    return TagsView;

});
