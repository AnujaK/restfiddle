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
    	$(selector).unbind("click").click(function(){
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
	    	"name" : $("#workspaceTextField").val()
	    });
	 //   newModel.save();
	    wkView.services.saveWorkspace(newModel.toJSON(),wkView.onSaveWorkspceSuc,wkView.onSaveWorkspceSuc);
	    wkView.model.add(newModel);
	    wkView.render();
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
