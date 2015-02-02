define(function(require) {
	
	"use strict";
	
	require("jquery");
	
	var APP = require('commons/ns');
    var UserEvents = {};
    UserEvents.FETCH = 'user:fetch';
    
   $("#manageUsersMenu").bind('click', function(){
		UserEvents.triggerFetch();
   });
   
   UserEvents.triggerFetch = function(){
	   APP.Events.trigger(UserEvents.FETCH);
    };
    return UserEvents;
});