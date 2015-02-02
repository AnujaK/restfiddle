define(function(require) {

    var Backbone = require('backbone');
    var TagModel = require('models/tag');
    var APP = require('commons/ns');
    var Tags = Backbone.Collection.extend({
	model : TagModel,
	url : APP.config.baseUrl + "/tags"
    });

    return Tags;
});
