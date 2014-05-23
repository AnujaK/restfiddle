/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var Workspaces = Backbone.Collection.extend({
	model : app.Workspace,
	url : "/api/workspaces"
    });

    app.workspaces = new Workspaces();
})();
