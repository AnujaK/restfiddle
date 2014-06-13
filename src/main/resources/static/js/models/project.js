/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    app.Project = Backbone.Model.extend({
	urlRoot : "/api/projects",
	defaults : {
	    name : '',
	    description : ''
	}
    });
})();
