define(function(require) {	
	
	"use strict";
	
	var ProjectEvents = {};
	ProjectEvents.CHANGE = 'project:change';
	
    ProjectEvents.triggerChange = function(newProjectId){
    	APP.Events.trigger(ProjectEvents.CHANGE, newProjectId);
    };
   
    return ProjectEvents;
});