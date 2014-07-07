/* global Backbone */
var app = app || {};


define(function(require) {
	
	var Backbone = require('backbone');
	var WorkspaceModel = require('models/workspace');

    var Workspaces = Backbone.Collection.extend({
		model : WorkspaceModel,
		url : app.config.baseUrl + "/workspaces"
    });

    return Workspaces;
});
