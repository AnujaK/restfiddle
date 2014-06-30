/* global Backbone */
var app = app || {};

(function() {
	'use strict';

	app.ApplicationModel = Backbone.Model.extend({
		defaults : {
			workspaceModelId :'',
			projectModelId : ''
		}
	});
})();
