define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
    var ConversationEvents = require('events/conversation-event');
    
    var NodeModel = require('models/node');
    var ConversationModel = require('models/conversation');
	
	var TaggedNodeListItemView = Backbone.View.extend({	
		tagName : 'li',
        template: _.template($('#tpl-tagged-node-list-item').html()),
		events : {
			"click a" : "showConversation"
		},
        
        showConversation : function(){
            var node = new NodeModel({
                id : this.model.id
            });
            node.fetch({
                success : function(response) {
                    console.log(response.get("conversation"));
                    var conversation = new ConversationModel(response.get("conversation"));
                    conversation.set("name", response.get("name"));
                    conversation.set("description",response.get("description"));
                    APP.conversation.render(conversation);
                    ConversationEvents.triggerChange(response.get("conversation") ? response.get("conversation").id : null);
                }
            });
            
        },
        
		render : function(eventName) {
            this.$el.html(this.template({node: this.model}));
			return this;
		}
	});
	
	var TaggedNodeView = Backbone.View.extend({
        el: '#tagged-items',
        
		initialize : function() {
			
		},
        
		showTaggedNodes : function(tagId){
            this.$el.html('');
            var me = this;
            $.ajax({
                url : APP.config.baseUrl + '/workspaces/1/tags/'+tagId+'/nodes',
                type : 'get',
                dataType : 'json',
                contentType : "application/json",
                success : function(response) {
                    $.each(response , function(i, node) { 
                        var taggedNodeListItemView = new TaggedNodeListItemView({
                            model : node
                        });
                        me.$el.append(taggedNodeListItemView.render().el);
                    });
                }
            });    		
		},	
        
		render : function(eventName) {
			return this;
		}
	});
	
	return TaggedNodeView;
	
});
