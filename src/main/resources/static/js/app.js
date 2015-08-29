define([ 'jquery',
         'bootstarp',
         'underscore',
         'commons/ns',
         'routers/app-router',
		 'commons/config',
		 'views/app-view'
         ], function($, Bootstarp, _, APP, AppRouter, config, AppView) {
	
	var init = function(){
		APP.appView = new AppView();
		APP.router = new AppRouter();	
	};
	return {
		initialize : init
	};
});
