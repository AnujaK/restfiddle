/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    'use strict';

    app.AppView = Backbone.View.extend({
	events : {

	},

	initialize : function() {
	    var view = new app.WorkspaceView({
		model : app.workspaces
	    });
	    this.render();
	},

	render : function() {
	    console.log("app-view#render");
	}
    });
})(jQuery);
