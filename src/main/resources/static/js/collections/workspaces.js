/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	

    var Workspaces = Backbone.Collection.extend({
	model : app.Workspace,
	url : "/api/workspaces"
    });

    app.workspaces = new Workspaces();
});
