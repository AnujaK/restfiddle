/* global Backbone */
var app = app || {};

define(function(require) {

	require('backbone');

	var WorkspaceModel = Backbone.Model.extend({
		urlRoot : app.config.baseUrl + "/workspaces",
		defaults : {
			name : '',
			description : ''
		}
	});
	return WorkspaceModel;
});
