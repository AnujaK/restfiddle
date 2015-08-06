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
                    APP.users.fetch({success : function(response){	
                        var userList = [];
                        response.each(function(user) {
                            userList.push(new UserModel(user));
                        });
				        var userView = new UserView({model : userList});
                        userView.handleUsers();
                        userView.showUsers();
			         }});
                    alert('User deleted successfully!');
				}
			});
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
                 $("#manageCollaboratorsModal").find('.modal-body').append('<a href="#" id = "addCollaborator">Add Collaborators</a>'+ '<form id = "addCollaboratorForm" style="display:none;">'+
					'<div class = "row"><div class = "col-lg-8 col-md-8 col-sm-10">	<br><input type="text" id="collaboratorName" class="form-control" name = "collaboratorName" placeholder="Name" required> <br>		<input type="email" id="collaboratorEmailId" class="form-control" name = "collaboratorEmailId" placeholder="Email Id" required><br>							<input type="password" id="collaboratorPassword" class="form-control" name = "collaboratorPassword" placeholder="Password" required>							<br>							<button type="button" class="btn btn-default pull-right" id = "saveCollaborator">Save</button>						</div>					</div>				</form>');
			}});
		},
		
		render : function(eventName) {
			console.log("UserView#render");
			return this;
		}
	});
	
	return UserView;
	
});
