define(function(require) {
	
	"use strict";
	
	require("jquery");
	
	var APP = require('commons/ns');
    var TagEvents = {};
    TagEvents.FETCH = 'tag:fetch';
    
//   $("#managerTagsMenu").bind('click', function(){
//		TagEvents.triggerFetch();
//   });
   
   TagEvents.triggerFetch = function(){
	   APP.Events.trigger(TagEvents.FETCH);
    };
    return TagEvents;
});