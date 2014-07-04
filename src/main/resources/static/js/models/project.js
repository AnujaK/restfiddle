/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	

    app.Project = Backbone.Model.extend({
	urlRoot : app.config.baseUrl +"/projects",
	defaults : {
		id : '',
		projectRef : '',
	    name : '',
	    description : ''
	}
    });
});
