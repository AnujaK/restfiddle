/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var Projects = Backbone.Collection.extend({
	model : app.Project,
	url : app.config.baseUrl + "/projects"
    });

    app.projects = new Projects();
})();
