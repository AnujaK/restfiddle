define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	
	var TaggedNodeListItemView = Backbone.View.extend({	
		tagName : 'li',
        template: _.template($('#tpl-tagged-node-list-item').html()),
		
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
            $.ajax({
                url : APP.config.baseUrl + '/workspaces/1/tags/'+tagId+'/nodes',
                type : 'get',
                dataType : 'json',
                contentType : "application/json",
                success : function(response) {
                    $.each(response , function(i, node) { 
                        var taggedNodeListItemView = new TaggedNodeListItemView({
                            model : node
                        });
                        me.$el.append(taggedNodeListItemView.render().el);
                    });
                }
            });    		
		},	
        
		render : function(eventName) {
			return this;
		}
	});
	
	return TaggedNodeView;
	
});
