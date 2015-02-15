define(function(require) {

	"use strict";

	require('jquery');
	require('fancytree');
	require('bootstarp');
	var ConversationEvents = require('events/conversation-event');
	var ConversationModel = require('models/conversation');
	var StarEvent = require('events/star-event');

	var NodeModel = require('models/node');
	var EntityModel = require('models/entity');
	var tree = {};

	var TreeNodeView = Backbone.View.extend({	
        template: _.template($('#tpl-tree-node').html()),
        
        initialize : function() {
            this.render();
		},
		
        render : function() {
            this.$el.html(this.template());
			return this;
		}
	});
    
    function editNode(node){
        //var node = $("#tree").fancytree("getActiveNode");
        if (node == null) {
            alert("Please select a node to edit.");
            return;
        }
        else if(node.data.nodeType == 'PROJECT'){
            alert("Please use 'Edit Project' menu to edit a project.");
            return;
        }
        $("#editNodeModal").modal("show");
        $("#editNodeTextField").val(node.data.name);
        $("#editNodeTextArea").val(node.data.description);
    }
    
	$("#requestBtn").bind("click", function() {
		$("#requestModal").find("#source").val("request");
		$("#requestModal").modal("show");
	});

	$("#saveAsConversationBtn").bind("click", function() {
		$("#requestModal").find("#source").val("conversation");
		$("#requestModal").modal("show");
	});
	
	$("#expandAllNodes").bind("click", function() {
		$("#tree").fancytree("getRootNode").visit(function(node){
			node.setExpanded(true);
		});
	});
	
	$("#collapseAllNodes").bind("click", function() {
		$("#tree").fancytree("getRootNode").visit(function(node){
			node.setExpanded(false);
		});
	});	

	$("#createNewRequestBtn").bind("click", function() {
		var conversation = null;
		if ($("#requestModal").find("#source").val() == 'request') {
			conversation = new ConversationModel({});

		} else if ($("#requestModal").find("#source").val() == 'conversation') {
			var rfRequest = {
				apiUrl : $("#apiUrl").val(),
				apiBody : APP.conversation.apiBodyCodeMirror.getValue(),
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
			nodeDesc : $("#requestTextArea").val(),
			conversation : conversation,
			successCallBack : function() {
				$("#requestModal").modal("hide");
				$("#requestModal").find("#requestName").val("");
				$("#requestModal").find("#requestTextArea").val("");
			}
		});

	});

	$("#createNewFolderBtn").bind("click", function() {
		tree.createNewNode({
			nodeName : $("#folderId").val(),
			nodeDesc : $("#folderTextArea").val(),
			conversation : null,
			successCallBack : function() {
				$("#folderModal").modal("hide");
			}
		});
	});

	$("#importFileBtn").unbind("click").bind("click", function() {
		var projectId = APP.appView.getCurrentProjectId();
		var fileInput = document.getElementById('importFileId');
		var file = fileInput.files[0];
		var fd = new FormData();
		fd.append('projectId', projectId);
		fd.append('name', '');
		fd.append('file', file);
		$.ajax({
			url : APP.config.baseUrl + '/import',
			type : 'post',
			processData: false,
			contentType: false,
			data : fd,
			success : function(response) {
				console.log("Import file response : "+response);
				$("#importModal").modal("hide");
				tree.showTree(tree.projectRefNodeId);
			}
		});
	});

	$("#createNewEntityBtn").unbind("click").bind("click", function() {
		var entityName = $("#newEntityName").val();
		var entityDescription = $("#newEntityDescription").val();

		var entityFields = getEntityFields();

		var entity = new EntityModel({
			id : null,
			name : entityName,
			description : entityDescription,
			fields : entityFields

		});

		tree.createNewNode({
			nodeName : entityName,
			nodeDesc : entityDescription,
			conversation : null,
			entity : entity,
			successCallBack : function() {
				$("#entityFieldsWrapper").html('');
				$("#entityModal").modal("hide");
			}
		});
	});

	var getEntityFields = function(){
		var fieldNames = [];
		$(".entityFieldName").each(function() {
			var fieldName = {};
			fieldName.name = $(this).val();
			fieldNames.push(fieldName);
		});  

		var fieldTypes = [];
		$(".entityFieldType").each(function() {
			var fieldType = {};
			fieldType.type = $(this).val();
			fieldTypes.push(fieldType);
		}); 

		var fieldArr = [];
		var counter = 0;
		$.each(fieldNames, function() {
			var field = {};
			field.name = fieldNames[counter].name;
			field.type = fieldTypes[counter].type;
			fieldArr.push(field);
			counter++;
		});  
		return fieldArr;
	};	
	$("#editNodeMenuItem").unbind("click").bind("click", function() {
		var node = $("#tree").fancytree("getActiveNode");
		if (node == null) {
			alert("Please select a node to edit.");
			return;
		}
		else if(node.data.nodeType == 'PROJECT'){
			alert("Please use 'Edit Project' menu to edit a project.");
			return;
		}
		$("#editNodeModal").modal("show");
		$("#editNodeTextField").val(node.data.name);
		$("#editNodeTextArea").val(node.data.description);

	});
   
	$("#editNodeBtn").unbind("click").bind("click", function() {
		var node = $("#tree").fancytree("getActiveNode");

		var nodeModel = new NodeModel({
			id : node.data.id,
			name : $("#editNodeTextField").val(),
			description : $("#editNodeTextArea").val()
		});

		node.data.id = nodeModel.attributes.id;
		node.data.name = nodeModel.attributes.name;
		node.data.description = nodeModel.attributes.description;
		node.setTitle(nodeModel.attributes.name+ '&nbsp;&nbsp;<div class="btn-group menu-arrow"><button type="button" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-angle-down" data-toggle="dropdown"></span></button><ul class="dropdown-menu"><li><i class="fa fa-pencil fa-fw"></i> Edit Node</li><li><i class="fa fa-trash-o fa-fw"></i> Delete Node</li><li><i class="fa fa-copy fa-fw"></i> Copy Node</li></ul></div>');

		nodeModel.save(null, {
			success : function(response) {
				$("#editNodeTextField").val("");
				$("#editNodeTextArea").val("");
				$("#editNodeModal").modal("hide");
			},
			error : function(e) {
				alert('Some unexpected error occured Please try later.');
			}
		});
	});

$("#deleteRequestBtn").bind("click", function() {
	var node = $("#tree").fancytree("getActiveNode");
	$("#deleteNodeModal").modal("hide");

	if (node == null) {
		alert("Please select a node to be deleted.");
		return;
	}

	$.ajax({
		url : APP.config.baseUrl + '/nodes/' + node.data.id,
		type : 'delete',
		dataType : 'json',
		contentType : "application/json",
		success : function() {
			node.remove();
		}
	});
});

$("#deleteProjectBtn").bind("click", function() {
	$.ajax({
		url : APP.config.baseUrl + '/workspaces/' + APP.appView.getCurrentWorkspaceId() + "/projects/" + $("#deleteProjectId").val(),
		type : 'delete',
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			location.reload();
		}
	});
});

$("#deleteWorkspaceBtn").bind("click", function() {
	$.ajax({
		url : APP.config.baseUrl + '/workspaces/' + APP.appView.getCurrentWorkspaceId(),
		type : 'delete',
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			location.reload();
		}
	});
});

$("#deleteTagBtn").bind("click", function() {
	$.ajax({
		url : APP.config.baseUrl + '/tags/' + $("#deleteTagId").val(),
		type : 'delete',
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			location.reload();
		}
	});
});

$('.col-1-toggle-btn').toggle(function() {
	$('.rf-col-1').hide();
	$('.rf-col-2').css('left', '0%');
	$('.rf-col-3').css('left', '33%');
	$('.rf-col-3').removeClass('col-xs-6').addClass("col-xs-8");
	$('#col1-toggle-icon').removeClass('fa-angle-double-left').addClass("fa-angle-double-right");

}, function() {
	$('.rf-col-1').show();
	$('.rf-col-2').css('left', '17%');
	$('.rf-col-3').css('left', '50%');
	$('.rf-col-3').removeClass('col-xs-8').addClass("col-xs-6");
	$('#col1-toggle-icon').removeClass('fa-angle-double-right').addClass("fa-angle-double-left");
});
$('.header-toggle-btn').toggle(function() {
	$('.navbar-fixed-top').hide();
	$('body').css('padding-top', '0px');
	$('#header-toggle-icon').removeClass('fa-angle-double-up').addClass("fa-angle-double-down");

}, function() {
	$('.navbar-fixed-top').show();
	$('body').css('padding-top', '50px');
	$('#header-toggle-icon').removeClass('fa-angle-double-down').addClass("fa-angle-double-up");
});

$('.right-pannel-toggle-btn').toggle(function() {
	$('.rf-col-1').hide();
	$('.rf-col-2').hide();
	$('.rf-col-3').css('left', '0%');
	$('.rf-col-3').removeClass('col-xs-6').addClass("col-xs-12");
	$('#full-screen-icon').removeClass('fa fa-arrows-alt').addClass("fa fa-angle-double-right");

}, function() {
	$('.rf-col-1').show();
	$('.rf-col-2').show();
	$('.rf-col-2').css('left', '17%');
	$('.rf-col-3').css('left', '50%');
	$('.rf-col-3').removeClass('col-xs-12').addClass("col-xs-6");
	$('#full-screen-icon').removeClass('fa fa-angle-double-right').addClass("fa fa-arrows-alt");
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
                    createNode: function(event, data) {
                        data.node.li.getElementsByClassName("edit-node")[0].addEventListener("click", function(){editNode(data.node);});
                      },
					click : function(event, data) {
						if (!data.node.isFolder() && data.node.data.id) {
							APP.Events.trigger(StarEvent.CLICK,data.node.data.id);
							var node = new NodeModel({
								id : data.node.data.id
							});
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
									conversation.set("id", conversation.get("id"));
									conversation.set("name", response.get("name"));
									conversation.set("description",response.get("description"));
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
	if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER' || serverNode.nodeType == 'ENTITY') {
		uiNode.folder = true;
		uiNode.id = serverNode.id;
		uiNode.name = serverNode.name;
		uiNode.description = serverNode.description;
		uiNode.nodeType = serverNode.nodeType;
        
        var treeNodeView = new TreeNodeView();
		if(serverNode.nodeType == 'ENTITY'){
			uiNode.title = '&nbsp;<span><i class="fa fa-database color-gray"></i></span>&nbsp;' + serverNode.name + treeNodeView.template();
		}
		else{
			uiNode.title = serverNode.name + treeNodeView.template();
		}
	}
	if (serverNode.children == undefined || serverNode.children.length == 0) {
		return;
	}

	uiNode.children = new Array();
	for (var i = 0; i < serverNode.children.length; i++) {
		if (serverNode.children[i].nodeType != 'FOLDER' && serverNode.children[i].nodeType != 'ENTITY') {
            var treeNodeView = new TreeNodeView();
			uiNode.children.push({
				title : serverNode.children[i].name + treeNodeView.template(),
				id : serverNode.children[i].id,
				name : serverNode.children[i].name,
				description : serverNode.children[i].description,
				nodeType : serverNode.children[i].nodeType
			});
		} else if (serverNode.children[i].nodeType == 'FOLDER' || serverNode.children[i].nodeType == 'ENTITY') {
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
	 	if(params.entity && params.entity != null){
	 		params.entity.save(null, {
	 			success : function(response) {
	 				console.log("response # save entity" + response);
	 				createNode(params.nodeName, params.nodeDesc, 'ENTITY', null, new EntityModel({
	 					id : response.get("id")
	 				}), params.successCallBack);
	 			}
	 		});
	 	}
	 	else if (params.conversation == null) {
	 		createNode(params.nodeName, params.nodeDesc, 'FOLDER', null, null, params.successCallBack);
	 	} else {
	 		params.conversation.save(null, {
	 			success : function(response) {
	 				createNode(params.nodeName, params.nodeDesc, null, new ConversationModel({
	 					id : response.get("id")
	 				}), null, params.successCallBack);
	 			}
	 		});
	 	}
	 };

	 var createNode = function(nodeName, nodeDesc, nodeType, conversation, entity, successCallBack) {
	 	var activeFolder = tree.getActiveFolder();
	 	var parentNodeId = activeFolder.data.id;
	 	var node = new NodeModel({
	 		parentId : parentNodeId,
	 		name : nodeName,
	 		description : nodeDesc,
	 		projectId : APP.appView.getCurrentProjectId(),
	 		conversationDTO : conversation,
	 		genericEntityDTO : entity,
	 		nodeType : nodeType
	 	});
	 	node.save(null, {
	 		success : function(response) {
	 			tree.appendChild(activeFolder, tree.convertModelToNode(response));
                //Refresh tree after an entity got created. as service apis will be generated in the back-end.
              /*  if(entity && entity != null){*/
                	tree.showTree(tree.projectRefNodeId);
                /*}*/
                successCallBack();
            },
            error : function(err) {
            	alert('error while saving folder'+err);
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
		tree.projectRefNodeId = projectRefNodeId;
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
				//Make the tree expanded once it is loaded
				$("#tree").fancytree("getRootNode").visit(function(node){
					node.setExpanded(true);
				});

				$('.menu-arrow').unbind("click").bind("click",function(event){
					event.stopPropagation();

					var currentElm = $(event.currentTarget);

					if(currentElm.hasClass('open')){
						$('.btn-group').removeClass('open');
						currentElm.removeClass('open');
					}else{
						var liElm = $(currentElm.closest('.fancytree-node'));
						if($(liElm.parent()).hasClass("fancytree-lastsib")){
							$(liElm.parent().parent()).css("overflow","");
						}
						$('.btn-group').removeClass('open');
						currentElm.addClass('open');
					}
				});
			}
		});
};

return tree;
});