/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    app.Project = Backbone.Model.extend({
	urlRoot : app.config.baseUrl +"/projects",
	defaults : {
		id : '',
		projectRef : '',
	    name : '',
	    description : ''
	}
    });
})();
