/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    app.Workspace = Backbone.Model.extend({
	urlRoot : app.config.baseUrl +"/workspaces",
	defaults : {
	    name : '',
	    description : ''
	}
    });
})();
