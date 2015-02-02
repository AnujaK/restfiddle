define(function(require) {
	
	require('backbone');
	

    var WorkspaceRouter = Backbone.Router.extend({
	routes : {

	}
    });

    APP.WorkspaceRouter = new WorkspaceRouter();
    Backbone.history.start();
});
