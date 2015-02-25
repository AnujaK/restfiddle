define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	var ConversationEvents = require('events/conversation-event');
	require('bootpag');
	
	var HistoryListItemView = Backbone.View.extend({	
		tagName : 'li',
		events : {
			"click a" : "renderConversationEvent"
		},
		template : _.template($('#tpl-history-list-item').html()),

		renderConversationEvent : function(){
			var conversation = new ConversationModel({
				id : $(event.currentTarget).data('historyId')
			});
			conversation.fetch({
				success : function(response) {
                    //TODO : Setting request name temporarily.
                    if(response.get('name') == null || response.get('name') == ''){
                    	response.set('name','Request');
                    }
                    APP.conversation.render(response);
                    ConversationEvents.triggerChange(response ? response.id : null);
                }
            });
		},
		
		render : function(pageNumber) {
			var start = 10 * (pageNumber-1);
			var end = start + 9;
			end = end > this.model.length-1 ? this.model.length-1 : end;
			return this.renderActivityGroup(start, end);
		},

		renderActivityGroup : function(start, end) {
			var that = this;
			this.$el.html('');
			var subset = _.filter(this.model, function(num, index){
				return (index >= start) && (index <= end);
			});
			
			_.each(subset, function (activity) {
				var activityTemplate = this.template({conversation : activity});
				$(this.el).append(activityTemplate);
			}, this); 

			return this;
		}
	});

var HistoryView = Backbone.View.extend({
	el: '#history-items',
	initialize : function() {
		
	},
	
	render : function(historyModel) {
		this.$el.html('');
		this.model = historyModel;
		
		var historyListItemView = new HistoryListItemView({model: this.model});

		this.$el.append('<div id="activity-pannel"></div><div class= "activity-paginator"></div>');
		$("#activity-pannel").html(historyListItemView.render(1).el);

		$('.activity-paginator').bootpag({
			total: Math.ceil(this.model.length/10),
			page: 1,
			maxVisible: 5
		}).on("page", function(event, num){
			$("#activity-pannel").html(historyListItemView.render(num).el);

		});
		return this;
	}
});

return HistoryView;

});
