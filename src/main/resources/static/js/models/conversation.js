define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var APP = require('commons/ns');
	var ConversationModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl +"/conversations/",
		defaults : {
			id : null,
			name : '',
			description : '',
			rfRequest : '',
			rfResponse : '',
            workspaceId : null
		},
		sync : function(method, model, options){
			if(method == 'create' || method == 'update'){
				model.unset("rfRequest");
				model.unset("rfResponse");
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});
	return ConversationModel;
});
