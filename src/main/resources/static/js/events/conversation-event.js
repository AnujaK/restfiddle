/* global Backbone */
var app = app || {};

define(function(require) {
	
	require('underscore');

    var conversationEvents = _.extend({}, Backbone.Events);
    
    conversationEvents.triggerChange = function(newConversationEventsId){
    	conversationEvents.trigger('change', newConversationEventsId);
    };
   
    app.conversationEvents = conversationEvents;
});