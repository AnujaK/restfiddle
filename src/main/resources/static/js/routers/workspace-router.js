/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	

    var WorkspaceRouter = Backbone.Router.extend({
	routes : {

	}
    });

    app.WorkspaceRouter = new WorkspaceRouter();
    Backbone.history.start();
});
