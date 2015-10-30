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
			fields : null,
			entityDataList : null, 
		},
		sync : function(method, model, options){
			if(options.params){
				options.url = this.url() + '?' + $.param(options.params);
			}
			
			if(method == 'create' || method == 'update'){
				model.unset("entityDataList");
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});
	return EntityModel;
});
