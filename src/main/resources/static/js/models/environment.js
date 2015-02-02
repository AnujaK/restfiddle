define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var APP = require('commons/ns');
	var EnvironmentModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl +"/environments/",
		defaults : {
			id : null,
			name : '',
			properties : null
		},
		sync : function(method, model, options){
			return Backbone.sync(method, model, options);		
		}
	});
	return EnvironmentModel;
});
