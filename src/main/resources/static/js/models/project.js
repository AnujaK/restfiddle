define(function(require) {

	"use strict";
	
	require('backbone');
	var APP = require('commons/ns');
	
	var ProjectModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl + "/projects",
		defaults : {
			id : null,
			projectRef : '',
			name : '',
			description : ''
		},
		sync : function(method, model, options){
			if(method == 'create' || method == 'update'){
				model.urlRoot = APP.config.baseUrl + "/workspaces/" + APP.appView.getCurrentWorkspaceId() + "/projects";
				model.unset('projectRef');
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});

	return ProjectModel;
});
