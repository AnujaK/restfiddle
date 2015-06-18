define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	var ConversationEvents = require('events/conversation-event');
	require('bootpag');
	require('moment');
	
	var HistoryListItemView = Backbone.View.extend({	
		tagName : 'li',
		events : {
			"click a" : "renderConversationEvent"
		},
		template : _.template($('#tpl-history-list-item').html()),

		renderConversationEvent : function(event){
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
			return this.renderActivityGroup();
		},

		 getColorCode : function(method){
            switch (method){
                case "GET" : return "blue";
                break;
                case "POST" : return "green";
                break;
                case "DELETE" : return "red";
                break;
                case "PUT" : return "orange";
                break;
            }
        },

		renderActivityGroup : function() {
			var that = this;
			var currentDate = moment(new Date());
			this.$el.html('');

            _.each(this.model, function (activity) {
                if(activity.lastModifiedDate){
                   var requestDiff = currentDate.diff(activity.lastModifiedDate,'days');
                   if(requestDiff == 0){
                    activity.time = currentDate.diff(activity.lastModifiedDate,'hours') + "h ago";
                   }else{
                    activity.time = moment(activity.lastModifiedDate).format('MMM DD hh:mma');
                   }
                }
                if(activity.runBy !== null){
                    activity.runBy = 'by '+ activity.runBy;
                }
                activity.className = "lozenge left " + that.getColorCode(activity.rfRequest.methodType) + " auth_required";
                var activityTemplate = that.template({conversation : activity});
                $(that.el).append(activityTemplate);
            }, this); 
            
			return this;
		}
	});

var HistoryView = Backbone.View.extend({
	el: '#history-items',
	initialize : function() {
		
	},
	
	render : function(response) {
		this.$el.html('');
        
        var historyList = response.data;
        var totalPages = response.totalPages;
        var page = response.page;
        var limit = response.limit;
        
		this.model = historyList;
		
		var historyListItemView = new HistoryListItemView({model: this.model});

		this.$el.append('<div id="activity-pannel"></div><div class= "activity-paginator"></div>');
		$("#activity-pannel").html(historyListItemView.render(1).el);

		$('.activity-paginator').bootpag({
            total: totalPages,
			page: 1,
			maxVisible: 5
		}).on("page", function(event, pageNumber){
            //fetch activity-log per page.
             var zeroBasedPageNumber = pageNumber - 1;
            $.ajax({
                url : APP.config.baseUrl + '/conversations?page='+zeroBasedPageNumber+'&limit=10',
                type : 'get',
                dataType : 'json',
                contentType : "application/json",
                success : function(response) {
                    var historyList = response.data;
                    historyListItemView = new HistoryListItemView({model: historyList});
                    $("#activity-pannel").html(historyListItemView.render(pageNumber).el);
                }
            });
		});
		return this;
	}
});

return HistoryView;

});
