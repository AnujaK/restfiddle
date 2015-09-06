define(function(require) {

	require('backbone');
	var APP = require('commons/ns');
	var AppView = require('views/app-view');

	var StarView = require("views/star-view");
	var ProjectEvents = require('events/project-event');
	var tree = require('views/tree-view');
	var Workspace = require('models/workspace');
	var WorkspaceView = require('views/workspace-view');
	var TaggedNodeView = require('views/tagged-node-view');

	var AppRouter = Backbone.Router.extend({

		initialize : function() {

		},

		routes : {
			"" : "handleDefault",
			"starred" : "showStarred",
			"activityLog" : "showHistory",
			"workspace/:workspaceId(/project/:projectId)" : "showProject",
			"workspace/:workspaceId/project/:projectId/node/:nodeId" : "showNode",
			"workspace/:workspaceId/tag/:tagId" : "showTagNodes"
		},

		handleDefault : function() {

		},

		showStarred : function() {
			$('#rf-col-1-body').find('li').each(function() {
				$(this).removeClass('active');
			});

			$(".starred").addClass('active');

			$.ajax({
				url : APP.config.baseUrl + '/nodes/starred',
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(response) {
					var starView = new StarView({
						model : response
					});
					starView.render();
					$('#starred-items').show();
				}
			});

			$('#tree').hide();
			$('#tagged-items').hide();
			$('#history-items').hide();

		},
		showHistory : function() {
			$('#rf-col-1-body').find('li').each(function() {
				$(this).removeClass('active');
			});
			$(".history").addClass('active');

			$.ajax({
				url : APP.config.baseUrl + '/conversations',
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(response) {
					if (response != undefined) {
						// var conversations = response.data;
						APP.historyView.render(response);
						$('#history-items').show();
					}
				}
			});

			$('#tree').hide();
			$('#tagged-items').hide();
			$('#starred-items').hide();
		},
		showProject : function(workspaceId, projectId, nodeId) {

			var workspace = new Workspace({
				id : workspaceId
			});
			workspace.fetch({
				success : function(response) {
					var workSpaceView = new WorkspaceView();
					workSpaceView.changeWorkspace(response);

					if (!projectId) {
						var projects = response.get('projects');
						if (projects[0]) {
							projectId = projects[0].id;
						}
					}

					if (projectId) {
						$('#rf-col-1-body').find('li').each(function() {
							$(this).removeClass('active');
						});

						var element = $('#' + projectId);
						element.parent('li').addClass('active');

						$('#tagged-items').hide();
						$('#starred-items').hide();
						$('#history-items').hide();
						$('#tree').show();

						console.log('Project Id : ' + element.data('project-ref-id'))
						ProjectEvents.triggerChange(projectId);
						console.log('current project id is ' + APP.appView.getCurrentProjectId());
						tree.showTree(element.data('project-ref-id'),nodeId);
					}
				}
			});

		},
		showNode : function(workspaceId, projectId, nodeId) {
			
			if(workspaceId === APP.appView.getCurrentWorkspaceId() && projectId === APP.appView.getCurrentProjectId() && $('#tree').is(':visible')){
				tree.loadNode(nodeId);
			}else{
				var workspace = new Workspace({
					id : workspaceId
				});
				workspace.fetch({
					success : function(response) {
						var workSpaceView = new WorkspaceView();
						workSpaceView.changeWorkspace(response);

						if (!projectId) {
							var projects = response.get('projects');
							if (projects[0]) {
								projectId = projects[0].id;
							}
						}

						if (projectId) {
							$('#rf-col-1-body').find('li').each(function() {
								$(this).removeClass('active');
							});

							var element = $('#' + projectId);
							element.parent('li').addClass('active');

							$('#tagged-items').hide();
							$('#starred-items').hide();
							$('#history-items').hide();
							$('#tree').show();

							console.log('Project Id : ' + element.data('project-ref-id'))
							ProjectEvents.triggerChange(projectId);
							console.log('current project id is ' + APP.appView.getCurrentProjectId());
							tree.showTree(element.data('project-ref-id'),nodeId);
						}
					}
				});
			}
		},
		showTagNodes : function(workspaceId,tagId) {
			var workspace = new Workspace({
				id : workspaceId
			});
			workspace.fetch({
				success : function(response) {
					var workSpaceView = new WorkspaceView();
					workSpaceView.changeWorkspace(response);

					$('#rf-col-1-body').find('li').each(function(){
						$(this).removeClass('active');
					});
					
					var element = $('#' + tagId);
					element.parent('li').addClass('active');
					
					$('#starred-items').hide();
					$('#tree').hide();
					$('#history-items').hide();
					$('#tagged-items').show();
					var taggedNodeView = new TaggedNodeView();
					taggedNodeView.showTaggedNodes(tagId);
				}
			});
		}
		

	});

	return AppRouter;
});
