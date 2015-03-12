define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
  var ConversationEvents = require('events/conversation-event');

  var NodeModel = require('models/node');
  var ConversationModel = require('models/conversation');

  var TaggedNodeListItemView = Backbone.View.extend({	
    tagName : 'li',
    template: _.template($('#tpl-tagged-node-list-item').html()),
    events : {
     "click a" : "showConversation"
   },

   showConversation : function(){
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

  },

  render : function(eventName) {
    this.$el.html(this.template({node: this.model}));
    return this;
  }
});

  var TaggedNodeView = Backbone.View.extend({
    el: '#tagged-items',

    initialize : function() {

    },

    showTaggedNodes : function(tagId){
      this.$el.html('');
      var me = this;
      this.$el.append('<div id="tagged-pannel"></div><div class= "tag-paginator"></div>');

      $.ajax({
        url : APP.config.baseUrl + '/workspaces/1/tags/'+tagId+'/nodes',
        type : 'get',
        dataType : 'json',
        contentType : "application/json",
        success : function(response) {
         me.render(1,response);
         $('.tag-paginator').bootpag({
          total: Math.ceil(response.length/10),
          page: 1,
          maxVisible: 5
        }).on("page", function(event, num){
          me.render(num,response);

        });
      }
    });    		
    },	

    render : function(pageNumber,response) {
     var start = 10 * (pageNumber-1);
     var end = start + 9;
     end = end > response.length-1 ? response.length-1 : end;
     return this.renderActivityGroup(start, end, response);
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

  renderActivityGroup : function(start, end,data) {
    $("#tagged-pannel").html('');
    var that = this;
    var subset = _.filter(data, function(num, index){
      return (index >= start) && (index <= end);
    });
    _.each(subset, function (activity) {
     var methodType = activity.conversation.rfRequest.methodType;
     if(methodType){
      activity.className = "lozenge left " + that.getColorCode(methodType) + " auth_required";
      activity.methodType = methodType;
    }
    var taggedNodeListItemView = new TaggedNodeListItemView({
      model : activity
    });
    $("#tagged-pannel").append(taggedNodeListItemView.render().el);
  }, this); 

    return this;
  }
});

return TaggedNodeView;

});
