/* global Backbone */
var app = app || {};

(function() {
	'use strict';

	app.ConversationModel = Backbone.Model.extend({
		defaults : {
			id :'',
			rfRequest : '',
			rfResponse : '', 
		}
	});
})();
