define(function(require) {
	
	"use strict";
	
	
	var Backbone = require('backbone');
	var ConversationModel = require('models/conversation');
    var EntityModel = require('models/entity');
	var APP = require('commons/ns');

	var NodeModel = Backbone.Model.extend({
		urlRoot : APP.config.baseUrl +"/nodes/",
		defaults : {
			id : null,
			name : '',
			description : '',
			parentId : '',
			projectId: '',
			nodeType : '',	
			conversationDTO : ConversationModel,
            genericEntityDTO : EntityModel
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
