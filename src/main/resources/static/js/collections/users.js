define(function(require) {

    var Backbone = require('backbone');
    var UserModel = require('models/user');
    var APP = require('commons/ns');
    var Users = Backbone.Collection.extend({
    	model : UserModel,
    	url : APP.config.baseUrl + "/users"
    });

    return Users;
});
