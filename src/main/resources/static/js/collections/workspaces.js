/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var Workspaces = Backbone.Collection.extend({
	model : app.Workspace
    });

    app.workspaces = new Workspaces();
})();
