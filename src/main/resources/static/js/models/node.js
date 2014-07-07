/* global Backbone */
var app = app || {};


define(function(require) {
	
	var Backbone = require('backbone');
	var ConversationModel = require('models/conversation');
	

	var NodeModel = Backbone.Model.extend({
		urlRoot : app.config.baseUrl +"/nodes/",
		defaults : {
			id : null,
			name : '',
			parentId : '',
			projectId: '',
			nodeType : '',
			conversationDTO : ConversationModel,
		},
		sync : function(method, model, options){
			if(method == 'create'){
				console.log(model);
				model.urlRoot = model.urlRoot + model.get('parentId') + '/children';
				if(model.get('conversationDTO') !=null){
					model.get('conversationDTO').unset('rfRequest');
					model.get('conversationDTO').unset('rfResponse');
				}
				return Backbone.sync(method, model, options);				
			}
			return Backbone.sync(method, model, options);		
		}
	});
	
	return NodeModel;
	
});
