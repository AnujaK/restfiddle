define(function (require) {
    "use strict";

    var Backbone = require('backbone');
    var _ = require('underscore');
    require('jquery');
    require('bootpag');
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

        renderConversationEvent : function(event){
            console.log("converstaion event clicked"+this.el);
            this.$el.parent('ul').find('li').each(function(){
                $(this).removeClass('active');
            });
            this.$el.addClass("active");
            console.log('Starred Node Id : ' + this.$el.find('a').data('data-star-id'));

            var node = new NodeModel({
                id : $(event.currentTarget).data('starId')
            });
            $("#currentStaredNode").val($(event.currentTarget).data('starId'));
            node.fetch({
                success : function(response) {
                    console.log(response.get("conversation"));
                    if(response.get("starred")){
                        $('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Unstar');
                    }
                    else{
                        $('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Star');
                    }
                    var conversation = new ConversationModel(response.get("conversation"));
                    conversation.set("name", response.get("name"));
                    conversation.set("description",response.get("description"));
                    APP.conversation.render(conversation);
                    APP.tagsLabel.display(response.get('tags'));
                    ConversationEvents.triggerChange(response.get("conversation") ? response.get("conversation").id : null);
                }
            });
            //ProjectEvents.triggerChange(this.$el.find('a').data('project-id'));
        },

        initialize: function () {
            console.log('called initialize');
        },
        render: function (pageNumber) {
            var start = 10 * (pageNumber-1);
            var end = start + 9;
            end = end > this.model.length-1 ? this.model.length-1 : end;
            return this.renderStarredGroup(start, end);
        },

        getColorCode : function(method){
            switch (method){
                case "GET" : return "blue";
                break;
                case "POST" : return "green";
                break;
                case "DELETE" : return "red";
                break;
                case "PUT" : return "orange";
                break;
            }
        },
        renderStarredGroup : function(start, end) {
            var that = this;
            this.$el.html('');
            var currentDate = moment(new Date());
            var subset = _.filter(this.model, function(num, index){
                return (index >= start) && (index <= end);
            });

            _.each(subset, function (starredRequest) {
                if(starredRequest.conversationDTO){
                	var conversation = starredRequest.conversationDTO;
                	if(conversation.lastModifiedDate){
                        var requestDiff = currentDate.diff(conversation.lastModifiedDate,'hours');
                        if(requestDiff == 0){
                            var min = currentDate.diff(conversation.lastModifiedDate,'minutes')
                            if(min > 1){
                            	starredRequest.time = min + " minutes ago";
                            }
                            else{
                            	starredRequest.time = min + " minute ago";
                            }
                        }
                        else if(requestDiff <= 1){
                        	starredRequest.time = requestDiff + ' hour ago';
						} else if (requestDiff < 24) {
							starredRequest.time = requestDiff + ' hours ago';
						} else {
							starredRequest.time = moment(conversation.lastModifiedDate).format('MMM DD hh:mma');
						}
                	}
                	
                	if(conversation.lastModifiedBy){
                		starredRequest.runBy = 'by '+ conversation.lastModifiedBy.name;
                    }
                	
                    var methodType = conversation.rfRequestDTO.methodType;
                    if(methodType){
                        starredRequest.className = "lozenge left " + that.getColorCode(methodType) + " auth_required";
                        starredRequest.methodType = methodType;
                    }
                }
               
                var starredTemplate = this.template({node : starredRequest});
                $(this.el).append(starredTemplate);
            }, this); 

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
            var nodeId;
            if($('#tree').css('display') == "none" && $('#currentStaredNode').val() != null){
               nodeId = $('#currentStaredNode').val();
            }else{
                nodeId = APP.appView.getCurrentRequestNodeId();
            }
            console.log("conversation id is ..." + APP.appView.getCurrentRequestNodeId());
            if(nodeId){
                var node = new NodeModel({
                    id : nodeId
                });
                node.fetch({
                    success : function(response) {
                     var starred = !response.get("starred");
                     var starModel = new StarModel();
                     starModel.set('id', nodeId);
                     starModel.set('starred', !response.get("starred"));
                     starModel.save(null, {
                        success: function () {
                            console.log("changes saves successfully");
                            if($('#starred-items').css('display') == 'block'){
                                $('.starred ').click();
                            }
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
    
        }
    },

    render: function () {
        //ToDo: When starred nodes will be workspace specific, add 'workspace/<workspaceId>' before "starred" in url
        window.history.pushState("", "starred", APP.config.root+"starred");
        this.$el.html('');
        var starListView = new StarListView({model: this.model});
        this.$el.append('<div id="star-request-pannel"></div><div class= "star-paginator"></div>');
        $("#star-request-pannel").html(starListView.render(1).el);

        $('.star-paginator').bootpag({
            total: Math.ceil(this.model.length/10),
            page: 1,
            maxVisible: 5
        }).on("page", function(event, num){
            $("#star-request-pannel").html(starListView.render(num).el);

        });

        return this;

    }
});

return StarView;

});
