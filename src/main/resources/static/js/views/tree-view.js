define(function(require) {

	"use strict";
	
	require('jquery');
	require('fancytree');
	require('bootstarp');
	var ConversationEvents = require('events/conversation-event');
	var ConversationModel = require('models/conversation');
	var NodeModel = require('models/node');
	var tree = {}

	$("#requestBtn").bind("click", function() {
		$("#requestModal").find("#source").val("request");
		$("#requestModal").modal("show");
	});

	$("#saveAsConversationBtn").bind("click", function() {
		$("#requestModal").find("#source").val("conversation");
		$("#requestModal").modal("show");
	})

	$("#createNewRequestBtn").bind("click", function() {
		var conversation = null
		if ($("#requestModal").find("#source").val() == 'request') {
			conversation = new ConversationModel({});

		} else if ($("#requestModal").find("#source").val() == 'conversation') {
			var rfRequest = {
				apiUrl : $("#apiUrl").val(),
				apiBody : $("#apiBody").val(),
				methodType : $(".apiRequestType").val()
			};
			var rfResponse = {

			};
			conversation = new ConversationModel({
				id : null,
				rfRequestDTO : rfRequest,
				rfResponseDTO : rfResponse

			});
		} else {
			console.log('source is not set properly for modal');
			alert('some error occurs');
		}

		tree.createNewNode({
			nodeName : $("#requestName").val(),
			conversation : conversation,
			successCallBack : function() {
				$("#requestModal").modal("hide");
			}
		});

	});

	$("#createNewFolderBtn").bind("click", function() {
		tree.createNewNode({
			nodeName : $("#folderId").val(),
			conversation : null,
			successCallBack : function() {
				$("#folderModal").modal("hide");
			}
		});
	});

	$('.col-1-toggle-btn').toggle(function() {
		$('.rf-col-1').hide();
		$('.rf-col-2').css('left', '0%');
		$('.rf-col-3').css('left', '33%');
		$('.rf-col-3').removeClass('col-xs-6').addClass("col-xs-8");

	}, function() {
		$('.rf-col-1').show();
		$('.rf-col-2').css('left', '17%');
		$('.rf-col-3').css('left', '50%');
		$('.rf-col-3').removeClass('col-xs-8').addClass("col-xs-6");
	});

	$("#tree").fancytree(
			{
				extensions : [ "glyph" ],
				glyph : {
					map : {
						doc : "glyphicon glyphicon-file",
						docOpen : "glyphicon glyphicon-file",
						checkbox : "glyphicon glyphicon-unchecked",
						checkboxSelected : "glyphicon glyphicon-check",
						checkboxUnknown : "glyphicon glyphicon-share",
						error : "glyphicon glyphicon-warning-sign",
						expanderClosed : "glyphicon glyphicon-plus-sign",
						expanderLazy : "glyphicon glyphicon-plus-sign",
						expanderOpen : "glyphicon glyphicon-minus-sign",
						folder : "glyphicon glyphicon-folder-close",
						folderOpen : "glyphicon glyphicon-folder-open",
						loading : "glyphicon glyphicon-refresh"
					}
				},
				dnd : {
					autoExpandMS : 400,
					focusOnClick : true,
					preventVoidMoves : true, // Prevent dropping nodes
												// 'before self', etc.
					preventRecursiveMoves : true, // Prevent dropping nodes on
													// own descendants
					dragStart : function(node, data) {
						/**
						 * This function MUST be defined to enable dragging for
						 * the tree. Return false to cancel dragging of node.
						 */
						return true;
					},
					dragEnter : function(node, data) {
						/**
						 * data.otherNode may be null for non-fancytree
						 * droppables. Return false to disallow dropping on
						 * node. In this case dragOver and dragLeave are not
						 * called. Return 'over', 'before, or 'after' to force a
						 * hitMode. Return ['before', 'after'] to restrict
						 * available hitModes. Any other return value will calc
						 * the hitMode from the cursor position.
						 */
						// Prevent dropping a parent below another parent (only
						// sort
						// nodes under the same parent)
						/*
						 * if(node.parent !== data.otherNode.parent){ return
						 * false; } // Don't allow dropping *over* a node (would
						 * create a child) return ["before", "after"];
						 */
						return true;
					},
					dragDrop : function(node, data) {
						/**
						 * This function MUST be defined to enable dropping of
						 * items on the tree.
						 */
						data.otherNode.moveTo(node, data.hitMode);
					}
				},
				click : function(event, data) {
					if (!data.node.isFolder() && data.node.data.id) {
						var node = new NodeModel({
							id : data.node.data.id
						});
						node.fetch({
							success : function(response) {
								console.log(response.get("conversation"));
								var conversation = new ConversationModel(
										response.get("conversation"));
								// var conversationView = new
								// app.ConversationView({model : conversation});
								APP.conversation.render(conversation);
								ConversationEvents.triggerChange(response
										.get("conversation") ? response
										.get("conversation").id : null);
							}
						});

					} else if (data.node.isFolder()) {
						var conversation = new ConversationModel({});
						// var conversationView = new
						// app.ConversationView({model : conversation});
						APP.conversation.render(conversation);
						ConversationEvents.triggerChange(null);
					}
				},
				source : []
			});

	var treeObj = $("#tree").fancytree("getTree");

	function nodeConverter(serverNode, uiNode) {
		if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER') {
			uiNode.folder = true;
			uiNode.id = serverNode.id
			uiNode.title = '<p>' + serverNode.name + '</p>';
		}
		if (serverNode.children == undefined || serverNode.children.length == 0) {
			return;
		}

		uiNode.children = new Array();
		for ( var i = 0; i < serverNode.children.length; i++) {
			if (serverNode.children[i].nodeType != 'FOLDER') {
				uiNode.children.push({
					title : '<p>' + serverNode.children[i].name + '</p>',
					id : serverNode.children[i].id
				});
			} else if (serverNode.children[i].nodeType == 'FOLDER') {
				uiNode.children.push({});
				nodeConverter(serverNode.children[i], uiNode.children[i]);
			}

		}
	}

	/**
	 * params params.nodeName : Name with which node get created
	 * params.conversation : Null or Object of conversation Model. If null will
	 * create folder else create Node with associated conversation object.
	 * params.successCallBack : Success call back function
	 * 
	 * This function fist create the conversation and create conversation
	 * associated node.
	 */
	tree.createNewNode = function(params) {
		if (params.conversation == null) {
			createNode(params.nodeName, 'FOLDER', null, params.successCallBack);
		} else {
			params.conversation.save(null, {
				success : function(response) {
					createNode(params.nodeName, null, new ConversationModel({
						id : response.get("id")
					}), params.successCallBack);
				}
			});
		}
	};

	var createNode = function(nodeName, nodeType, conversation, successCallBack) {
		var activeFolder = tree.getActiveFolder();
		var parentNodeId = activeFolder.data.id;
		var node = new NodeModel({
			parentId : parentNodeId,
			name : nodeName,
			projectId : APP.appView.getCurrentProjectId(),
			conversationDTO : conversation,
			nodeType : nodeType
		});
		node.save(null, {
			success : function(response) {

				tree.appendChild(activeFolder, tree
						.convertModelToNode(response));

				successCallBack();
			},
			error : function() {
				alert('error while saving folder');
			}
		});
	};

	tree.appendChild = function(parent, child) {
		var childNode = parent.addChildren(child);
		childNode.setActive(true);
		$(childNode.li).trigger('click');
		/*
		 * console.log(childNode)
		 * app.conversationEvents.triggerChange(childNode.data.id);
		 */
	};
	tree.convertModelToNode = function(nodeModel) {
		return {
			title : nodeModel.get('name'),
			id : nodeModel.get('id'),
			folder : nodeModel.get('nodeType') == 'FOLDER' ? true : false
		};
	};
	tree.resetTree = function() {
		treeObj.reload([]);
	};
	tree.getActiveFolder = function() {
		var node = $("#tree").fancytree("getActiveNode");
		var folder = getParentFolder(node);
		if (folder) {
			return folder;
		} else {
			// return root folder
			return $("#tree").fancytree("getRootNode").getFirstChild();
		}

	};

	var getParentFolder = function(node) {
		if (node) {
			if (node.isFolder()) {
				return node;
			} else if (node.getParent().isFolder()) {
				return node.getParent();
			} else {
				return getParentFolder(node.getParent());
			}
		} else {
			return null;
		}
	};
	tree.showTree = function(projectRefNodeId) {
		$.ajax({
			url : APP.config.baseUrl + '/nodes/' + projectRefNodeId + '/tree',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(serviceSideTreeData) {
				console.log("server side tree data : ");
				console.log(serviceSideTreeData);
				var uiTree = [];
				var uiSideTreeData = {};
				nodeConverter(serviceSideTreeData, uiSideTreeData);
				console.log("client side tree data : ");
				console.log(uiSideTreeData);
				uiTree.push(uiSideTreeData);
				treeObj.reload(uiTree);
			}
		});
	};

	return tree;
});