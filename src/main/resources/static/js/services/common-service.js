/* global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function($) {
	function commonService(){
		
	}
	commonService.prototype = {
		saveWorkspace : function(workSpaceModel,successcb,failcb){
			var url = "";
			apiRestRequestPost(url, workSpaceModel, successcb, "", failcb);
		}	
	};
	app.commonService = commonService;
})(jQuery);