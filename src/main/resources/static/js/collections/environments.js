define(function(require) {

    var Backbone = require('backbone');
    var EnvironmentModel = require('models/environment');
    var APP = require('commons/ns');
    var Environments = Backbone.Collection.extend({
    	model : EnvironmentModel,
    	url : APP.config.baseUrl + "/environments"
    });

    return Environments;
});
