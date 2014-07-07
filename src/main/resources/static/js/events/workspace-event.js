/* global Backbone */
var app = app || {};


define(function(require) {
	
	

    var WorkspaceEvents = {};
    WorkspaceEvents.CHANGE = 'workspace:change';
    WorkspaceEvents.FETCH = 'workspace:fetch';
    
   $("#switchWorkSpace").bind('click', function(){
    	console.log('called switch work space');
    	WorkspaceEvents.triggerFetch();
    });
   
   WorkspaceEvents.triggerFetch = function(){
	   app.Events.trigger(WorkspaceEvents.FETCH);
    };
    
    WorkspaceEvents.triggerChange = function(newWorkspaceId){
    	app.Events.trigger( WorkspaceEvents.CHANGE, newWorkspaceId);
    };
    
   
   
    return WorkspaceEvents;
});