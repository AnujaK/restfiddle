define(function(require) {

	"use strict";
	
	require('backbone');
	var APP = require('commons/ns');
	var WorkspaceModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl + "/workspaces",
		defaults : {
			name : '',
			description : ''
		}
	});
	return WorkspaceModel;
});
