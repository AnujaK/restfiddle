var app = app || {};

(function($) {
    'use strict';
    function Config() {
	this.root = (function(pathname) {
	    var match = new RegExp("/[^/]*/").exec(pathname);
	    return match === null ? "/" : match[0];
	})(window.location.pathname);
	this.baseUrl = this.root + "api";
	console.log("base url is : "+this.baseUr);
    }
    app.config = new Config();
})();