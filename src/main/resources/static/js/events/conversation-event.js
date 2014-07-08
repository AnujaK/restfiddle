define(function(require) {

	"use strict";

    var ConversationEvents = {};
    ConversationEvents.CHANGE = 'change';
    
    ConversationEvents.triggerChange = function(newConversationEventsId){
    	APP.Events.trigger('change', newConversationEventsId);
    };
   
   return ConversationEvents;
});