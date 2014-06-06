/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    'use strict';

    app.WorkspaceView = Backbone.View.extend({
	tagName : 'li',
	el : '#dd-workspace-wrapper',

	template : _.template($('#tpl-workspace-list-item').html()),
	services : new app.commonService(),
	events : {

	},
	bindEvent : function(selector,callback){
		var self = this;
    	$(selector).click(function(){
    		if(callback){
    		callback();
    		}
    	});
	},
	initialize : function() {
		_(this).bindAll('saveWorkSpace');
	    this.listenTo(app.workspaces, 'sync', this.render);
	    app.workspaces.fetch();
	},

	render : function(eventName) {
		var self = this;
	    console.log(eventName + ">>>" + JSON.stringify(this.model.toJSON()));
	    $(this.el).html(this.template({
		list : this.model.toJSON()
	    }));
	    // we are binding event using jquery because save button is part of Modal window and modal window is not a child of el mentioned baove.
	    self.bindEvent("#saveWorkspaceBtn",self.saveWorkSpace);
	    // $('#tree').append(view.render().el);
	    // console.log(eventName + ">>>" + app.workspaces.toJSON());
	    // console.log(eventName + ">>>" + JSON.stringify(app.workspaces.toJSON()[0]));
	    return this;
	},
	saveWorkSpace : function(){
		var wkView = this;
	    var newModel = new app.Workspace();
	    newModel.set({
	    	"name" : "check"
	    });
	 //   newModel.save();
	    wkView.services.saveWorkspace(newModel.oJSON(),wkView.onSaveWorkspceSuc,wkView.onSaveWorkspceSuc);
	    wkView.model.add(newModel);
	},
	onSaveWorkspceSuc : function(responseData){
	    $('#workspaceModal').modal("hide");
		alert("success");
	},
	onSaveWorkspceFail : function(){
		alert("failed");
	},
	clear : function() {
	    this.model.destroy();
	}
    });
})(jQuery);
