/* global Backbone */
var app = app || {};

(function() {
    'use strict';

    var Projects = Backbone.Collection.extend({
	model : app.Project,
	url : "/api/projects"
    });

    app.projects = new Projects();
})();
