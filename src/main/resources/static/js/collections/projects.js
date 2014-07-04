/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	
    var Projects = Backbone.Collection.extend({
	model : app.Project,
	url : "/api/projects"
    });

    app.projects = new Projects();
});
