define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var APP = require('commons/ns');
	var tree = require('views/tree-view');
	var ProjectEvents = require('events/project-event');
	
	var ProjectView = Backbone.View.extend({
		el : '#test_project',
		addOne : function(model){
			var projectListView = new ProjectListView({model: model});
			this.$el.append(projectListView.render().el);
			projectListView.$el.find('a').trigger('click');
			return this;
		},
		render : function(isDefautlView){
			this.$el.html('');
			_.each(this.model,function(p, index){
				var projectListView = new ProjectListView({model: p});
				this.$el.append(projectListView.render().el);
				if(index == 0){
					projectListView.$el.find('a').trigger('click');
				}
			},this);
		}
	});
	
	var ProjectListView = Backbone.View.extend({
		tagName : 'li',
		events : {
			"click a" : "showProjectTree",
			"click .hover-down-arrow" : "preventParentElmSelection",
            "click .edit-project" : "editProject",
            "click .delete-project" : "deleteProject",
            "click .export-project" : "exportProject"
		},
		template : _.template($('#tpl-project-list-item').html()),
		
		initialize : function() {
			console.log('called initialize');
			//this.render();
		},

		preventParentElmSelection : function(event){
			event.stopPropagation();
			
			var currentElm = $(event.currentTarget);

			if(currentElm.hasClass('open')){
				$('.btn-group').removeClass('open');
				currentElm.removeClass('open');
			}else{
				$('.btn-group').removeClass('open');
				currentElm.addClass('open');
                var rect = event.currentTarget.getBoundingClientRect();
			    currentElm.children("ul").css({"position": "fixed", "left":rect.left , "top": rect.bottom});
			}
			
		},
        
        editProject : function(){
            $("#editProjectId").val(this.model.get('id'));
            $("#editProjectTextField").val(this.model.get('name'));
            $("#editProjectTextArea").val(this.model.get('description'));
            $("#editProjectModal").modal("show");
        },
        
        deleteProject : function(){
            $("#deleteProjectId").val(this.model.get('id'));
            $("#deleteProjectModal").modal("show");
        },
        exportProject : function(){
           var projectRefId = this.model.get('projectRef').id;
           var url = window.location.protocol+"//"+location.host + APP.config.baseUrl + '/nodes/' + projectRefId + '/tree';  
           window.open(url);
        },        
		showProjectTree : function(){
			//this.$el.parent('ul').find('li').each(function(){
				//$(this).removeClass('active');
			//});

            $('#rf-col-1-body').find('li').each(function(){
                $(this).removeClass('active');
            });
            this.$el.addClass("active");

            $('#tagged-items').hide();
            $('#starred-items').hide();
            $('#history-items').hide();
            $('#tree').show();

            console.log('Project Id : ' + this.$el.find('a').data('project-id'))
            ProjectEvents.triggerChange(this.$el.find('a').data('project-id'));
            console.log('current project id is ' + APP.appView.getCurrentProjectId());
            tree.showTree(this.$el.find('a').data('project-ref-id'));
        },
        render : function() {
            this.$el.html(this.template({project : this.model.toJSON()}));
            return this;
        }
});

return ProjectView;
});
