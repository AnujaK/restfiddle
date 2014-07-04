/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	

    var Workspaces = Backbone.Collection.extend({
	model : app.Workspace,
	url : app.config.baseUrl + "/workspaces"
    });

    app.workspaces = new Workspaces();
});
