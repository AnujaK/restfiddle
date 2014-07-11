define(['backbone','models/starred'],function(Backbone,starredModel) {

    'use strict';

    var StarredCollection = Backbone.Collection.extend({
    		model : starredModel,
    		url : APP.config.baseUrl + "/stars"
    });

    return StarredCollection;

});