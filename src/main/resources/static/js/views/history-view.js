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
            var nodeName = '';
            var historyId;
			conversation.fetch({
				success : function(response) {
                    historyId = response.get('nodeId');
                    $.ajax({
                        url : APP.config.baseUrl + '/nodes/'+historyId,
                        type : 'get',
                        dataType : 'json',
                        contentType : "application/json",
                        success : function(response) {
                             $('#apiRequestName').html(response.name);
                        }
                    });
                    
                    if(nodeName == ''){
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
                   var requestDiff = currentDate.diff(activity.lastModifiedDate,'hours');
                   if(requestDiff == 0){
                       var min = currentDate.diff(activity.lastModifiedDate,'minutes')
                       if(min > 1){
                            activity.time = min + " minutes ago";
                       }
                       else{
                            activity.time = min + " minute ago";
                       }
                    
                   }
                   else if(requestDiff <= 1){
                    activity.time = requestDiff + ' hour ago';
                   } 
                   else if(requestDiff < 24){
                    activity.time = requestDiff + ' hours ago';
                   }
                   else{
                    activity.time = moment(activity.lastModifiedDate).format('MMM DD hh:mma');
                   }
                }
                if(activity.lastModifiedBy !== null){
                    activity.runBy = 'by '+ activity.lastModifiedBy.name;
                }
                activity.className = "lozenge left " + that.getColorCode(activity.rfRequestDTO.methodType) + " auth_required";
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
        //ToDo: When starred nodes will be workspace specific, add 'workspace/<workspaceId>' before "starred" in url
        window.history.pushState("", "activityLog", APP.config.root+"activityLog");
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
