define(function (require) {

    "use strict";

    require("jquery");

    var APP = require('commons/ns');
    var StarEvents = {};

    StarEvents.FETCH = 'star:fetch';
    StarEvents.SAVE = 'star:save';
    StarEvents.CLICK = 'node:click';

    $("#starNodeBtn").unbind("click").bind("click", function () {
        APP.Events.trigger(StarEvents.SAVE);
        console.log("Fire a star a node Event");
    });

    StarEvents.triggerFetch = function () {
        APP.Events.trigger(StarEvents.FETCH);
    };

    StarEvents.triggerSave = function () {
        APP.Events.trigger(StarEvents.SAVE);
    };

    StarEvents.triggerSave = function () {
        APP.Events.trigger(StarEvents.CLICK);
    };

    return StarEvents;
});