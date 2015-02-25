require.config({

	paths : {
		jquery : 'libs/jquery-1.7.2',
		jqueryUi : 'libs/jquery-ui.min',
		underscore : 'libs/underscore-min',
		backbone : 'libs/backbone-min',
		bootstarp : 'libs/bootstrap.min',
		fancytree : 'libs/jquery.fancytree-all',
		selectize : 'libs/selectize.min',
		mCustomScrollbar : 'libs/jquery.mCustomScrollbar.concat.min',
		text : 'libs/require/text',
		typeahead : 'libs/typeahead.bundle.min',
		zeroClipboard : 'libs/zeroClipboard',
		bootpag : 'libs/bootpag.min'
	},
	shim : {
		'underscore' : {
			exports : '_'
		},
		'jqueryUi' : {
			deps : ['jquery']
		},
		'bootstarp':{
			deps : ['jquery', 'jqueryUi']
		},
		'fancytree':{
			deps : ['jquery', 'jqueryUi']
		},
		'common/base':{
			deps : ['underscore']
		},
		'views/conversation-view/' :{
			deps : ['jquery']
		},
		'views/app-view/' :{
			deps : ['jquery']
		}
		
	}
});


define(['app'],function(app) {
	app.initialize();
});
