define(function(require) {

	require('backbone');
	var APP = require('commons/ns');
	var Projects = Backbone.Collection.extend({
		model : APP.Project,
		url : APP.config.baseUrl + "/projects"
	});

	APP.projects = new Projects();
});
