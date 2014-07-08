define(function(require) {
	
	var Backbone = require('backbone');
	var WorkspaceModel = require('models/workspace');
	var APP = require('commons/ns');
    var Workspaces = Backbone.Collection.extend({
		model : WorkspaceModel,
		url : APP.config.baseUrl + "/workspaces"
    });

    return Workspaces;
});
