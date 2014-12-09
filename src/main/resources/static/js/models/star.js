define(function (require) {

    "use strict";


    var Backbone = require('backbone');
    var APP = require('commons/ns');

    var StarModel = Backbone.Model.extend({
        urlRoot: APP.config.baseUrl + "/nodes/",
        defaults: {
            id: null,
            starred: true
        },
        sync: function (method, model, options) {
            if (method == 'create') {
                console.log(model);
                model.urlRoot = model.urlRoot + model.get('parentId')
            }
            return Backbone.sync(method, model, options);
        }
    });

    return StarModel;

});
