define(function(require) {

	"use strict";
	
	require('backbone');
	var APP = require('commons/ns');
	var WorkspaceModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl + "/workspaces",
		defaults : {
			id : null,
			name : '',
			description : ''
		},
		sync : function(method, model, options){
			if(method == 'create' || method == 'update'){
				model.urlRoot = APP.config.baseUrl + "/workspaces/";
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});
	return WorkspaceModel;
});
