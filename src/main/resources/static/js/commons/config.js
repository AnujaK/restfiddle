define(function(require) {
	
	require('underscore');
	var xyz = function(_) {
	    'use strict';
	    _.templateSettings = {
		evaluate : /<@([\s\S]+?)@>/g,
		interpolate : /<@=([\s\S]+?)@>/g,
		escape : /<@-([\s\S]+?)@>/g
	    };
	    return _;
	};
	xyz(_);
	

});
