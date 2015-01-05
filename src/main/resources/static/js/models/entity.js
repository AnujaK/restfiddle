define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var APP = require('commons/ns');
	var EntityModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl +"/entities/",
		defaults : {
			id : null,
			name : '',
			description : '',
			fields : '',
			entityDataList : '', 
		},
		sync : function(method, model, options){
			if(method == 'create' || method == 'update'){
				model.unset("fields");
				model.unset("entityDataList");
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});
	return ConversationModel;
});
