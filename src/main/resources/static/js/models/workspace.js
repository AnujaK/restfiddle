/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    app.Workspace = Backbone.Model.extend({
	urlRoot : "/api/workspaces",
	defaults : {
	    name : '',
	    description : ''
	}
    });
})();
