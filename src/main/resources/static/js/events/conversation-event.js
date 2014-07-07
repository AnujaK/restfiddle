/* global Backbone */
var app = app || {};

define(function(require) {
	
	require('underscore');

    var ConversationEvents = {};
    ConversationEvents.CHANGE = 'change';
    
    ConversationEvents.triggerChange = function(newConversationEventsId){
    	app.Events.trigger('change', newConversationEventsId);
    };
   
   return ConversationEvents;
});