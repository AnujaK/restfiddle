/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
	'use strict';

	app.ConversationView = Backbone.View.extend({
		el : '#conversationSection',
		initialize : function(){
			this.$el.find("#saveConversationBtn").click(function(view){
				return function(){view.saveOrUpdateConversation.call(view)};
			}(this));
		},
		render : function(conversation) {
			console.log('conversation rendnig with model');
			console.log(conversation);
			var request = conversation.get('rfRequest');
			var response = conversation.get('rfResponse');
			
			this.$el.find("#apiUrl").val(request.apiUrl);
			this.$el.find(".apiRequestType").val(request.methodType);
			this.$el.find("#apiBody").val(request.apiBody);
			//this.$el.find("#response-wrapper").val(response.body);
		},
		saveOrUpdateConversation : function(){
			if(app.appView.getCurrentConversationId() != null){
				var rfRequest = {
						apiUrl : this.$el.find("#apiUrl").val(),
						apiBody : this.$el.find("#apiBody").val(),
						methodType : this.$el.find(".apiRequestType").val()
				}
				var rfResponse = {
						
				}
				var conversation = new app.ConversationModel({
					id : app.appView.getCurrentConversationId(),
					rfRequestDTO : rfRequest,
					rfResponseDTO : rfResponse
					
				});
				conversation.save(null, {
					success: function(){
						alert('Changes saved successfully!');
					},
					error : function(){
						alert('some error occured while saving the request');
					}
				});
			}else{
				alert('Pending : Create new conversation');
			}
		}
	});
})(jQuery);
