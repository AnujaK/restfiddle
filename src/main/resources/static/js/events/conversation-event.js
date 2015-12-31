define(function(require) {

	"use strict";

    var ConversationEvents = {};
    ConversationEvents.CHANGE = 'change';
    
    ConversationEvents.triggerChange = function(newConversationEventsId){
    	APP.Events.trigger('change', newConversationEventsId);
    };
    
    $("#showLastResponse").bind('click', function(){
        // More->Show Last response has been hidden for now
    });

   
   return ConversationEvents;
});