define(['backbone', 'jquery', 'collections/starred', 'text!templates/starredItem.tpl'], function (Backbone, $, StarredNodes, starredItem) {
    'use strict';

    var StarredListView = Backbone.View.extend({
        el: 'div #starredItems',

        initialize: function () {
            _.bindAll(this, 'render');
            this.collection = new StarredNodes();
            this.collection.bind('reset', this.render)
            this.collection.fetch();
            this.render();
        },

        render: function () {
            var sg = this;
            this.$el.append('<ul></ul>');
            _(this.collection.models).each(function (node) {
                var starredItemView = new StarredItemView({model: node}).render();
                sg.$el.append(starredItemView.el);
            });
            return this;
        }
    });

    var StarredView = Backbone.View.extend({
        el: 'li #starredItem',
        template: _.template($(starredItem).filter("#tpl-starred-list-item").html()),
        render: function () {
            this.$el.append(this.template({node: this.model}));
        }
    });

    return StarredListView;
});