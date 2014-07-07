/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	

    var ProjectModel = Backbone.Model.extend({
	urlRoot : app.config.baseUrl +"/projects",
	defaults : {
		id : '',
		projectRef : '',
	    name : '',
	    description : ''
	}
    });
    
    return ProjectModel;
});
