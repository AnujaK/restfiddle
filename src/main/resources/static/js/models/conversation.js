/* global Backbone */
var app = app || {};


define(function(require) {
	
	require('backbone');
	
	app.ConversationModel = Backbone.Model.extend({
		urlRoot : app.config.baseUrl +"/conversations/",
		defaults : {
			id : null,
			rfRequest : '',
			rfResponse : '', 
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
});
