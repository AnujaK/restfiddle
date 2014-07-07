require.config({

	paths : {
		jquery : 'libs/jquery-1.7.2',
		jqueryUi : 'libs/jquery-ui.min',
		underscore : 'libs/underscore-min',
		backbone : 'libs/backbone-min',
		bootstarp : 'libs/bootstrap.min',
		fancytree : 'libs/jquery.fancytree-all',
		text : 'libs/require/text'
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
		}
		
	}
});

var app = app || {};


define(function(require) {
	
	"use strict";
	
	require('jquery');
	require('bootstarp');
	require('underscore');
	require('backbone');
	
	require('commons/base');
	require('fancytree');
	require('libs/prettify/prettify');
	require("utils/util");
	require("commons/config");
	require("utils/caller"); //TODO: REMOVE THIS
	
	app.Events = _.extend({}, Backbone.Events);
	var AppView = require('views/app-view');
	var ConversationView = require('views/conversation-view');
	var ApplicationModel = require('models/app');

	
	app.appModel = new ApplicationModel();
	app.appView = new AppView();
	app.conversation = new ConversationView();
});
