/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var conversationEvents = _.extend({}, Backbone.Events);
    
    conversationEvents.triggerChange = function(newConversationEventsId){
    	conversationEvents.trigger('change', newConversationEventsId);
    };
   
    app.conversationEvents = conversationEvents;
})();