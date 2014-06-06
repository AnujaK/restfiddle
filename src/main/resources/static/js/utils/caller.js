/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

$(function() {
    'use strict';

    function send() {
	var item = {
	    apiUrl : $("#apiUrl").val(),
	    methodType :$(".apiRequestType").val()
	};

	$.ajax({
	    url : '/api/processor',
	    type : 'post',
	    dataType : 'json',
	    contentType : "application/json",
	    success : function(response) {
		console.log("####" + response);
		$("#response-wrapper").html(JSON.stringify(response));
	    },
	    data : JSON.stringify(item)
	});
    }

    function saveWorkspace() {
	var newWorkspace = new app.Workspace({
	    name : $("#workspaceTextField").val(),
	});
	newWorkspace.save();
	// app.workspaces.fetch();
	// newWorkspace.render();
    }

    $("#run").bind("click", send);
    $("#saveWorkspaceBtn").bind("click", saveWorkspace);

});