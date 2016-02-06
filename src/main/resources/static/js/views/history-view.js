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
                            //ToDo: Put the following code in a separate function
                            var iframe = document.getElementById('response-preview');
                            iframe = (iframe.contentWindow) ? iframe.contentWindow : (iframe.contentDocument.document) ? iframe.contentDocument.document : iframe.contentDocument;
                            iframe.document.open();		
                            var responseBody = response.conversation.rfResponse.body;
                            $("#response-wrapper").html('<br><pre class="prettyprint">' + responseBody.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</pre>');
                            iframe.document.write('<br><pre>' + responseBody + '</pre>');

                            iframe.document.close();
                            iframe.document.body.style.wordWrap = 'break-word';

                            $("body,html").animate({
                            scrollTop : $('#responseContainer').offset().top
                            }, "slow");
                            
                            var assertResults = response.conversation.rfRequest.assertion.bodyAsserts;
                            if(assertResults.length > 0){
                                var assertView = new BodyAssertResultView({model : assertResults});
                                $('#res-assert-wrapper tbody').remove();
                                $('#res-assert-wrapper').append(assertView.render().el);
                                var successCount = assertView.getSuccessCount();
                                $('#assertResultCount').html(successCount+'/'+assertResults.length);
                                var spans = $('#res-tab-assert').find('span');
                                $(spans[0]).html(successCount);
                                $(spans[1]).html(assertResults.length - successCount);
                            }

                            prettyPrint();
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
        
        showSavedResponse : function(response){
            var iframe = document.getElementById('response-preview');
            iframe = (iframe.contentWindow) ? iframe.contentWindow : (iframe.contentDocument.document) ? iframe.contentDocument.document : iframe.contentDocument;
            iframe.document.open();						
            //ToDo:Check if it is possible to show response image or file download as well
            $("#response-wrapper").html('<br><pre class="prettyprint">' + response.body.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</pre>');
            iframe.document.write('<br><pre>' + response.conversation.rfResponse.body + '</pre>');
            // }

            iframe.document.close();
            iframe.document.body.style.wordWrap = 'break-word';

            $("body,html").animate({
            scrollTop : $('#responseContainer').offset().top
            }, "slow");
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
                activity.className = "lozenge left " + that.getColorCode(activity.data[0].rfRequest.methodType) + " auth_required";
                var activityTemplate = that.template({activity : activity});
                $(that.el).append(activityTemplate);
            }, this); 
            
			return this;
		}
	});
    
    //Todo: Check if this rewriting of BodyAssertResultView code can be omitted by using the one from conversation-view
    var BodyAssertResultView = Backbone.View.extend({
		template : _.template($('#assert-result-list-item').html()),
		
		tagName: 'tbody',

		render : function() {
			return this.renderAssertResult();
		},

		renderAssertResult : function(){
			this.successCount = 0;
			
			 _.each(this.model, function (assert) {
				assert.status = assert.success ? "Success" : "Failure";
				assert.iconClass = assert.success ? "success-icon" : "failure-icon";
				var templ = this.template({result : assert});
                this.$el.append(templ);
                if(assert.success)
                	this.successCount++;
			 }, this);
			 
			 
			 return this;
		},
		
		getSuccessCount : function(){
			return this.successCount;
		}
		
		
	});


var HistoryView = Backbone.View.extend({
	el: '#history-items',
	initialize : function() {
		
	},
	
	render : function(response) {
        //ToDo: When starred nodes will be workspace specific, add 'workspace/<workspaceId>' before "starred" in url
        var workspaceId = APP.appView.getCurrentWorkspaceId();
		this.$el.html('');
        
        var historyList = response.content;
        var totalPages = response.totalPages;
        var page = response.number;
        var limit = response.size;
        
		this.model = historyList;
		
		var historyListItemView = new HistoryListItemView({model: this.model});
        
        var showSearchResults = function(search, sortBy){
        $.ajax({
            url : APP.config.baseUrl 
            + '/logs?workspaceId='+workspaceId+ (search ? '&search=' + search : '') + (sortBy ? '&sortBy=' + sortBy : ''),
            type : 'get',
            dataType : 'json',
            contentType : "application/json",
            success : function(response) {
                APP.historyView.render(response);
            }
            });
        };

		this.$el.append('<div id="activity-pannel"></div><div class= "activity-paginator"></div>');
		$("#activity-pannel").html(historyListItemView.render(1).el);
		
		$('#search').unbind().bind('keydown', function (e) {
            if (e.which == 13) {
                showSearchResults($("#search").val());
            }
		});
        
        $('#searchbtn').unbind().bind('click', function (e) {
            showSearchResults($("#search").val());
		});
        
        $('#sortOptionsDropdown').unbind().bind('click', function (e) {
                if(e.target.id === 'sortByName'){
                    showSearchResults($("#search").val(),'name');
                } else if(e.target.id === 'sortByLastModified'){
                    showSearchResults($("#search").val(), 'lastRun');
                } else if(e.target.id === 'sortByNameDesc'){
                    showSearchResults($("#search").val(), 'nameDesc');
                }
            });

		$('.activity-paginator').bootpag({
            total: totalPages,
			page: 1,
			maxVisible: 5
		}).on("page", function(event, pageNumber){
            //fetch activity-log per page.
             var zeroBasedPageNumber = pageNumber - 1;
            $.ajax({
                url : APP.config.baseUrl 
                + '/logs?workspaceId='+workspaceId+'&page='+zeroBasedPageNumber+'&limit=10&search=' + $('#search').val(),
                type : 'get',
                dataType : 'json',
                contentType : "application/json",
                success : function(response) {
                    var historyList = response.content;
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
