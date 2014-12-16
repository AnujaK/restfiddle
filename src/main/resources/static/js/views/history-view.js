define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	var ConversationEvents = require('events/conversation-event');
    
	var HistoryListItemView = Backbone.View.extend({	
		tagName : 'li',
        events : {
            "click a" : "renderConversationEvent"
        },
		template : _.template($('#tpl-history-list-item').html()),

        renderConversationEvent : function(){
            var conversation = new ConversationModel({
                id : this.model.id
            });
            conversation.fetch({
                success : function(response) {
                    APP.conversation.render(response);
                    ConversationEvents.triggerChange(response ? response.id : null);
                }
            });
        },
        
		render : function(eventName) {
			$(this.el).html(this.template({
				conversation : this.model
			}));
			return this;
		}
	});
	
	var HistoryView = Backbone.View.extend({
        el: '#history-items',
		initialize : function() {
			
		},
		
		render : function(historyModel) {
            this.model = historyModel;
            this.$el.html('');
            _.each(this.model, function (index) {
                var historyListItemView = new HistoryListItemView({model: index});
                this.$el.append(historyListItemView.render().el);
            }, this);
            
            return this;
		}
	});
	
	return HistoryView;
	
});
