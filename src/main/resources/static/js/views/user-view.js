define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	
	var UserModel = require('models/user');
	var UserEvents = require('events/user-event');

	var UserListItemView = Backbone.View.extend({
	
		template : _.template($('#tpl-user-list-item').html()),
		
		events : {
			"click .deleteUser" : "deleteUser",
			"click #deleteCollaborator" : "deleteCollaborator"
		},

		render : function(eventName) {
			$(this.el).html(this.template({
				user : this.model.toJSON()
			}));
			return this;
		},
		
		deleteUser : function(eventName){
			$.ajax({
				url : APP.config.baseUrl + '/users/' + this.model.get('id'),
				type : 'delete',
				contentType : "application/json",
				success : function(data) {
					$("#manageCollaboratorsModal").modal("hide");
                    APP.users.fetch({success : function(response){
                    $("#rfUsers").html('');
				    response.each(function(user) {
					$("#rfUsers").append("<li>&nbsp;&nbsp;<span class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;"+user.attributes.name+"</li>");
				    });				
			         }});
                    alert('User deleted successfully!');
				}
			});
		},

		deleteCollaborator : function(event){
			console.log(event);
		}
	});
	
	var UserView = Backbone.View.extend({
		initialize : function() {
			this.listenTo(APP.Events, UserEvents.FETCH, this.handleUsers);
		},
		showUsers : function(){
			APP.users.fetch({success : function(response){
				console.log('fetched users');
				$("#rfUsers").html('');
				response.each(function(user) {
					$("#rfUsers").append("<li>&nbsp;&nbsp;<span class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;"+user.attributes.name+"</li>");
					console.log("user" + user.attributes.name);
					
				});				
			}});			
		},

		handleUsers : function(event){
			APP.users.fetch({success : function(response){
				$("#manageCollaboratorsModal").find('.modal-body').html('');
				response.each(function(user) {
					var userListView = new UserListItemView({
						model : user
					});
					$("#manageCollaboratorsModal").find('.modal-body').append(userListView.render().el);
				});
			}});
		},
		
		render : function(eventName) {
			console.log("UserView#render");
			return this;
		}
	});
	
	return UserView;
	
});
