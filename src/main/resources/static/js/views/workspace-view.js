/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    'use strict';

    app.WorkspaceView = Backbone.View.extend({
	tagName : 'li',

	// template : _.template($('#item-template').html()),

	events : {

	},

	initialize : function() {
	    this.listenTo(app.workspaces, 'sync', this.render);
	},

	render : function(eventName) {
	    console.log(eventName + ">>>" + app.workspaces.toJSON());
	    // console.log(eventName + ">>>" + JSON.stringify(app.workspaces.toJSON()[0]));
	    return this;
	},

	clear : function() {
	    this.model.destroy();
	}
    });
})(jQuery);
