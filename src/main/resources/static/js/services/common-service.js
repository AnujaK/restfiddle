/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
    function commonService() {

    }
    function apiRestRequestPost(url, postData, callbackOnSuccess, options, callbackOnFailure) {
	var defaultOptions = {
	    url : url,
	    success : function(response) {
		if (callbackOnSuccess) {
		    callbackOnSuccess(response);
		}
	    },
	    type : "POST",
	    data : JSON.stringify(postData),
	    dataType : "json",
	    contentType : "application/json",
	    mimeType : "application/json",
	    async : true,
	    error : function(jqXHR, textStatus, errorThrown) {
		if (callbackOnFailure) {
		    callbackOnFailure(jqXHR, textStatus, errorThrown);
		}
	    }
	};
	options = $.extend(defaultOptions, options);

	$.ajax(options);
    }
    function apiRestRequestGet(url, postData, callbackOnSuccess, options, callbackOnFailure) {
	var defaultOptions = {
	    url : url,
	    success : function(response) {
		if (callbackOnSuccess) {
		    callbackOnSuccess(response);
		}
	    },
	    type : "GET",
	    async : false,
	    error : function(jqXHR, textStatus, errorThrown) {
		if (callbackOnFailure) {
		    callbackOnFailure(jqXHR, textStatus, errorThrown);
		}
	    }
	};

	options = $.extend(defaultOptions, options);
	$.ajax(options);
    }

    app.apiRestRequestPost = apiRestRequestPost;
    app.apiRestRequestGet = apiRestRequestGet;

    commonService.prototype = {
	saveWorkspace : function(workSpaceModel, successcb, failcb) {
	    var url = "/api/workspaces";
	    app.apiRestRequestPost(url, workSpaceModel, successcb, "", failcb);
	},
	saveProject : function(workspaceId, projectModel, successcb, failcb) {
	    var url = "api/workspaces/" + workspaceId + "/projects";
	    app.apiRestRequestPost(url, projectModel, successcb, "", failcb);
	}
    };
    app.commonService = commonService;

})(jQuery);