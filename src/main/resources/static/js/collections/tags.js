define(function(require) {

    var Backbone = require('backbone');
    var TagModel = require('models/tag');
    var APP = require('commons/ns');
    var Tags = Backbone.Collection.extend({
    	initialize: function(models, options) {
    	    this.id = options.id;
    	},
    	model : TagModel,
    	url : function() { 
    		return APP.config.baseUrl + "/workspaces/" + this.id + "/tags";
    	}
    });

    return Tags;
});
