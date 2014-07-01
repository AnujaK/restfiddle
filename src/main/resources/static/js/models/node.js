/* global Backbone */
var app = app || {};

(function() {
	'use strict';

	app.NodeModel = Backbone.Model.extend({
		urlRoot : "/api/nodes/",
		defaults : {
			id :'',
			name : ''
		}
	});
	
	
})();
