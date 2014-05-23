/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var WorkspaceRouter = Backbone.Router.extend({
	routes : {

	}
    });

    app.WorkspaceRouter = new WorkspaceRouter();
    Backbone.history.start();
})();
