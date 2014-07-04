define(['backbone'],function(Backbone){
	
	var ApplicationModel = Backbone.Model.extend({
		defaults : {
			workspaceModelId :'',
			projectModelId : ''
		}
	});
	
	return ApplicationModel;
	
});

/*
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
*/