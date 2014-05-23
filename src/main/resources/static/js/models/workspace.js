/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    app.Workspace = Backbone.Model.extend({
	defaults : {
	    name : '',
	    description : ''
	}
    });
})();
