/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    'use strict';

    app.AppView = Backbone.View.extend({
	el : '#dd-workspace',

	events : {

	},

	initialize : function() {
	    this.$workspace = this.$('#dd-workspace');

	    app.workspaces.fetch();

	    var view = new app.WorkspaceView({
		model : app.workspaces
	    });
	},

	render : function() {
	    console.log("app-view#render");
	}
    });
})(jQuery);
