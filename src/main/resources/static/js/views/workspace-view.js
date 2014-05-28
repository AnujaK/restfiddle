/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    'use strict';

    app.WorkspaceView = Backbone.View.extend({
	tagName : 'li',
	el : '#dd-workspace-wrapper',

	template : _.template($('#tpl-workspace-list-item').html()),

	events : {

	},

	initialize : function() {
	    this.listenTo(app.workspaces, 'sync', this.render);
	    app.workspaces.fetch();
	},

	render : function(eventName) {
	    console.log(eventName + ">>>" + JSON.stringify(this.model.toJSON()));
	    $(this.el).html(this.template({
		list : this.model.toJSON()
	    }));
	    // $('#tree').append(view.render().el);
	    // console.log(eventName + ">>>" + app.workspaces.toJSON());
	    // console.log(eventName + ">>>" + JSON.stringify(app.workspaces.toJSON()[0]));
	    return this;
	},

	clear : function() {
	    this.model.destroy();
	}
    });
})(jQuery);
