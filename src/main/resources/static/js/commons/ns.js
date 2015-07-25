define(function(require){
	
	var APP = window.APP || {};
	
	
	function Config() {
		this.root = (function(pathname) {
			var match = new RegExp("/[^/]*/").exec(pathname);
			return match === null ? "/" : match[0];
		})(window.location.pathname);
		this.baseUrl = this.root + "api";
	}
	APP.config = new Config();
	
	window.APP = APP;
	return window.APP;

});

