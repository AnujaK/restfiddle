define(function(require) {

    "use strict";

    require('backbone');
    var APP = require('commons/ns');
    var UserModel = Backbone.Model.extend({
	urlRoot : APP.config.baseUrl + "/users",
	defaults : {
	    id : null,
	    name : '',
	    description : ''
	},
	sync : function(method, model, options) {
	    if (method == 'create' || method == 'update') {
		model.urlRoot = APP.config.baseUrl + "/users/";
		return Backbone.sync(method, model, options);
	    }
	    return Backbone.sync(method, model, options);
	}
    });
    return UserModel;
});
