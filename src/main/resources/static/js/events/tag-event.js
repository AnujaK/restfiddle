define(function(require) {
	
	"use strict";
	
	require("jquery");
	
	var APP = require('commons/ns');
    var TagEvents = {};
    TagEvents.FETCH = 'tag:fetch';
    TagEvents.SAVE = 'tag:save';
    
//   $("#managerTagsMenu").bind('click', function(){
//		TagEvents.triggerFetch();
//   });
   
   TagEvents.triggerFetch = function(){
	   APP.Events.trigger(TagEvents.FETCH);
    };

    TagEvents.triggerSave = function () {
        APP.Events.trigger(TagEvents.SAVE);
    };
    return TagEvents;
});