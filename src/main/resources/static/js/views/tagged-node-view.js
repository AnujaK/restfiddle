define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
  var ConversationEvents = require('events/conversation-event');

  var NodeModel = require('models/node');
  var ConversationModel = require('models/conversation');
    
  var selectedTagId;

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
    $("#currentStaredNode").val(this.model.id);
    node.fetch({
      success : function(response) {
        console.log(response.get("conversation"));
        var conversation = new ConversationModel(response.get("conversation"));
        conversation.set("name", response.get("name"));
        conversation.set("description",response.get("description"));
        APP.conversation.render(conversation);
        APP.tagsLabel.display(response.get('tags'));
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
      selectedTagId = tagId;
      this.$el.html('');
      var me = this;
      this.$el.append('<div id="tagged-pannel"></div><div class= "tag-paginator"></div>');

      $.ajax({
        url : APP.config.baseUrl +'/workspaces/' + APP.appView.getCurrentWorkspaceId() + '/tags/'+tagId+'/nodes',
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

    render : function(pageNumber, response) {
        var start = 10 * (pageNumber-1);
        var end = start + 9;
        var me = this;
        
        $('#search').unbind().bind('keydown', function (e) {
            if (e.which == 13) {
                me.showSearchResults($("#search").val());
            }
        });

        $('#searchbtn').unbind().bind('click', function (e) {
                me.showSearchResults($("#search").val());
        });
        
        $('#sortOptionsDropdown').unbind().bind('click', function (e) {
            if(e.target.id === 'sortByName'){
                me.showSearchResults($("#search").val(),'name');
            } else if(e.target.id === 'sortByLastModified'){
                me.showSearchResults($("#search").val(), 'lastRun');
            }else if(e.target.id === 'sortByNameDesc'){
                me.showSearchResults($("#search").val(), 'nameDesc');
            }
        });
        
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

      showSearchResults : function(search, sortBy){
        var me = this;
        $.ajax({
            url : APP.config.baseUrl +'/workspaces/' + APP.appView.getCurrentWorkspaceId() + '/tags/'
            + selectedTagId + '/nodes'+(search ? '?search=' + search + '&' : '?') + (sortBy ? 'sortBy=' + sortBy : ''),
            type : 'get',
            dataType : 'json',
            contentType : "application/json",
            success : function(res) {
                me.render(1,res);
                $('.tag-paginator').bootpag({
                    total: Math.ceil(res.length/10),
                    page: 1,
                    maxVisible: 5
                }).on("page", function(event, num){
                    me.render(num,res);
                });
            }
        });
    },

  renderActivityGroup : function(start, end,data) {
    $("#tagged-pannel").html('');
    var that = this;
    var currentDate = moment(new Date());
    var subset = _.filter(data, function(num, index){
      return (index >= start) && (index <= end);
    });
    _.each(subset, function (activity) {
      if(activity.conversationDTO){
    	var conversation = activity.conversationDTO;
      	if(conversation.lastModifiedDate){
          var requestDiff = currentDate.diff(conversation.lastModifiedDate,'hours');
          if(requestDiff == 0){
              var min = currentDate.diff(conversation.lastModifiedDate,'minutes')
              if(min > 1){
            	  activity.time = min + " minutes ago";
              }
              else{
            	  activity.time = min + " minute ago";
              }
          }
          else if(requestDiff <= 1){
        	  activity.time = requestDiff + ' hour ago';
			} else if (requestDiff < 24) {
				activity.time = requestDiff + ' hours ago';
			} else {
				activity.time = moment(conversation.lastModifiedDate).format('MMM DD hh:mma');
			}
      	}
      	
      	if(conversation.lastModifiedBy){
      		activity.runBy = 'by '+ conversation.lastModifiedBy.name;
          }
        var methodType = conversation.rfRequestDTO.methodType;
        if(methodType){
          activity.className = "lozenge left " + that.getColorCode(methodType) + " auth_required";
          activity.methodType = methodType;
        }
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
