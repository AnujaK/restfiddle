/* global Backbone */
var app = app || {};

define(function(require) {
	
	var ProjectEvents = {};
	
	ProjectEvents.CHANGE = 'project:change';
	
    ProjectEvents.triggerChange = function(newProjectId){
    	app.Events.trigger(ProjectEvents.CHANGE, newProjectId);
    };
   
    return ProjectEvents;
});