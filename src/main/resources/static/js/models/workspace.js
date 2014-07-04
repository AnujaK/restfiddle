/* global Backbone */
var app = app || {};

define(function(require) {
	
	require('backbone');
	
    app.Workspace = Backbone.Model.extend({
	urlRoot : app.config.baseUrl +"/workspaces",
	defaults : {
	    name : '',
	    description : ''
	}
    });
    
});
