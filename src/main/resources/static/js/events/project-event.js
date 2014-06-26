/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var projectEvents = _.extend({}, Backbone.Events);
    
    projectEvents.triggerChange = function(newProjectId){
    	projectEvents.trigger('change', newProjectId);
    };
   
    app.projectEvents = projectEvents;
})();