define(function(require) {
	
	"use strict";
	
	require('underscore');

    var ConversationEvents = {};
    ConversationEvents.CHANGE = 'change';
    
    ConversationEvents.triggerChange = function(newConversationEventsId){
    	APP.Events.trigger('change', newConversationEventsId);
    };
   
   return ConversationEvents;
});