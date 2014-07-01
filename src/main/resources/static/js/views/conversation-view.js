/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
	'use strict';

	app.ConversationView = Backbone.View.extend({
		el : '#conversationSection',
		initialize : function() {

		},

		render : function() {
			console.log('conversation rendnig with model');
			console.log(this.model);
			var request = this.model.get('rfRequest');
			var response = this.model.get('rfResponse');
			
			this.$el.find("#apiUrl").val(request.apiUrl);
			this.$el.find(".apiRequestType").val(request.methodType);
			this.$el.find("#apiBody").val(request.apiBody);
			//this.$el.find("#response-wrapper").val(response.body);
		}
	});
})(jQuery);
