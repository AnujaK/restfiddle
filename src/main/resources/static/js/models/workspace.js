/* global Backbone */
var app = app || {};

define(function(require) {
	
	require('backbone');
	
    app.Workspace = Backbone.Model.extend({
	urlRoot : "/api/workspaces",
	defaults : {
	    name : '',
	    description : ''
	}
    });
    
});
