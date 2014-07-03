/* global $ */
/* jshint unused:false */
var app = app || {};

$(function() {
    'use strict';
 
   app.appModel = new app.ApplicationModel(); 
   app.appView =  new app.AppView();
   app.conversation = new app.ConversationView();
});
