define(function (require) {
    "use strict";

    var Backbone = require('backbone');
    var _ = require('underscore');
    require('jquery');
    var StarEvents = require('events/star-event');
    var ConversationEvents = require('events/conversation-event');
    var ProjectEvents = require('events/project-event');
    var StarModel = require('models/star');
    var NodeModel = require('models/node');
    var ConversationModel = require("models/conversation");


    var StarListView = Backbone.View.extend({
        tagName: 'li',
        events : {
            "click a" : "renderConversationEvent"
        },
        template: _.template($('#tpl-star-list-item').html()),

        renderConversationEvent : function(){
            console.log("converstaion event clicked"+this.el);
            this.$el.parent('ul').find('li').each(function(){
                $(this).removeClass('active');
            })
            this.$el.addClass("active");
            console.log('Starred Node Id : ' + this.$el.find('a').data('data-star-id'));

            var node = new NodeModel({
                id : this.model.id
            });
            node.fetch({
                success : function(response) {
                    console.log(response.get("conversation"));
                    var conversation = new ConversationModel(response.get("conversation"));
                    conversation.set("name", response.get("name"));
                    conversation.set("description",response.get("description"));
                    APP.conversation.render(conversation);
                    ConversationEvents.triggerChange(response.get("conversation") ? response.get("conversation").id : null);
                }
            });
            //ProjectEvents.triggerChange(this.$el.find('a').data('project-id'));
        },

        initialize: function () {
            console.log('called initialize');
        },
        render: function () {
            this.$el.html(this.template({node: this.model}));
            return this;
        }
    });


    var StarView = Backbone.View.extend({
        el: '#starred-items',
        addOne: function (model) {
            var starListView = new StarListView({model: model});
            this.$el.append(starListView.render().el);
            return this;
        },
        initialize: function () {
            this.listenTo(APP.Events, StarEvents.SAVE, this.markAsStarred);
        },
        markAsStarred: function () {
            console.log("Event star a node was fired for node Id " + "");
            if (APP.appView.getCurrentRequestNodeId() != null) {
                console.log("conversation id is ..." + APP.appView.getCurrentRequestNodeId());
                var node = new NodeModel({
                    id : APP.appView.getCurrentRequestNodeId()
                });
                node.fetch({
                    success : function(response) {
                    	var starred = !response.get("starred");
                        var starModel = new StarModel();
                        starModel.set('id', APP.appView.getCurrentRequestNodeId());
                        starModel.set('starred', !response.get("starred"));
                        starModel.save(null, {
                            success: function () {
                                console.log("changes saves successfully");
                                if(starred){
                                	$('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Unstar');
                                }
                                else{
                                	$('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Star');
                                }
                            },
                            error: function () {
                                alert('some error occured while saving the request');
                            }
                        });
                    }
                });
            }
        },

        render: function () {
            this.$el.html('');
            _.each(this.model, function (index) {
                var starListView = new StarListView({model: index});
                this.$el.append(starListView.render().el);
            }, this);
        }
    });

    return StarView;

});
	
