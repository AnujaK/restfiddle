/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

$(function() {
    'use strict';

    function send() {
	var item = {
	    apiUrl : $("#apiUrl").val(),
	    methodType : "GET"
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
    function apiRestRequestPost(url, postData, callbackOnSuccess, options, callbackOnFailure){
    	        var ad = this;
    	        var defaultOptions = {
    	            url: url,
    	            success: function (response) {
    	            	if(callbackOnSuccess){
    	            	callbackOnSuccess(response);
    	            	}
    	            },
    	            type: "POST",
    	            data: JSON.stringify(postData),
    	            dataType: "json",
    	            contentType: "application/json",
    	            mimeType: "application/json",
    	            async: true,
    	            error: function (jqXHR, textStatus, errorThrown) {
    	                if(callbackOnFailure){
    	                	callbackOnFailure(jqXHR, textStatus, errorThrown);
    	                }
    	            }
    	        };
    	        options = $.extend(defaultOptions, options);

    	        $.ajax(options);
    		};
    		function apiRestRequestGet (url, postData, callbackOnSuccess, options, callbackOnFailure){
            var defaultOptions = {
                url: url,
                success: function (response) {
	            	if(callbackOnSuccess){
    	            	callbackOnSuccess(response);
    	            	}
                },
                type: "GET",
                async: false,
                error: function (jqXHR, textStatus, errorThrown) {
	                if(callbackOnFailure){
	                	callbackOnFailure(jqXHR, textStatus, errorThrown);
	                }
                }
            };

            options = $.extend(defaultOptions, options);
            $.ajax(options);
    	};

});