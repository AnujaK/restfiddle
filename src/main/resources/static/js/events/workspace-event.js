define(function(require) {
	
	"use strict";
	
	require("jquery");
	var APP = require('commons/ns');
    var WorkspaceEvents = {};
    WorkspaceEvents.CHANGE = 'workspace:change';
    WorkspaceEvents.FETCH = 'workspace:fetch';
    
   $("#switchWorkSpace").bind('click', function(){
    	console.log('called switch work space');
    	WorkspaceEvents.triggerFetch();
    });
   
   WorkspaceEvents.triggerFetch = function(){
	   APP.Events.trigger(WorkspaceEvents.FETCH);
    };
    
    WorkspaceEvents.triggerChange = function(newWorkspaceId){
    	APP.Events.trigger( WorkspaceEvents.CHANGE, newWorkspaceId);
    };
    
   
   
    return WorkspaceEvents;
});