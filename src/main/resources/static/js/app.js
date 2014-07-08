define([ 'jquery',
         'bootstarp',
         'underscore',
         'commons/ns',
		 'commons/config',
		 'views/app-view'
         ], function($, Bootstarp, _, APP, config, AppView) {
	
	var init = function(){
		APP.appView = new AppView();
	};
	return {
		initialize : init
	};
});
