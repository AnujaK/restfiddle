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
		
	}
});

var app = app || {};


define(function(require) {
	
	"use strict";	
	require('jquery');
	//require('jqueryUi');
	require('bootstarp');
	require('underscore');
	require('backbone');
	
	require('commons/base');
	require('fancytree');
	require('libs/prettify/prettify');
	require("utils/caller")
	require("services/common-service")
	require("utils/tree")
	require("models/workspace")
	require("models/conversation")
	require("models/node")
	require("collections/workspaces")
	require("events/workspace-event")
	require("events/project-event")
	require("events/conversation-event")
	require("views/workspace-view")
	require("views/project-view")
	require("routers/workspace-router")
	require("models/project")
	require("collections/projects")
	require("utils/util")
	require("commons/config")
	
	
	
	var AppView = require('views/app-view');
	var ConversationView = require('views/conversation-view');
	var ApplicationModel = require('models/app');
	
	app.appModel = new ApplicationModel();
	app.appView = new AppView();
	app.conversation = new ConversationView();
	
	/*$("#requestModal").modal("show");
	alert('yo ho');*/
	
});
