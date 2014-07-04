/* global Backbone */
var app = app || {};

define(function(require) {
	
	require('underscore');

    var projectEvents = _.extend({}, Backbone.Events);
    
    projectEvents.triggerChange = function(newProjectId){
    	projectEvents.trigger('change', newProjectId);
    };
   
    app.projectEvents = projectEvents;
});