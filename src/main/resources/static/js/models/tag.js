define(function(require) {

    "use strict";

    require('backbone');
    var APP = require('commons/ns');
    var TagModel = Backbone.Model.extend({
	urlRoot : APP.config.baseUrl + "/tags",
	defaults : {
	    id : null,
	    name : '',
	    description : ''
	},
	sync : function(method, model, options) {
	    if (method == 'create' || method == 'update') {
		model.urlRoot = APP.config.baseUrl + "/tags/";
		return Backbone.sync(method, model, options);
	    }
	    return Backbone.sync(method, model, options);
	}
    });
    return TagModel;
});
