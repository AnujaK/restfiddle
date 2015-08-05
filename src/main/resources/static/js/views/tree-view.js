define(function(require) {

	"use strict";

	require('jquery');
	require('fancytree');
	require('bootstarp');
	require("libs/jquery.validate");
	var ConversationEvents = require('events/conversation-event');
	var ConversationModel = require('models/conversation');
	var StarEvent = require('events/star-event');

	var NodeModel = require('models/node');
	var EntityModel = require('models/entity');
	var TagView = require('views/tag-view');
	var TagsView = require('views/tags-view');
	var treeData;
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

	var getColorCode = function(method){
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
	};

	
	function editNode(node){
		if (node == null) {
			alert("Please select a node to edit.");
			return;
		}
		else if(node.data.nodeType == 'PROJECT'){
			alert("Please use 'Edit Project' menu to edit a project.");
			return;
		}
		$("#editNodeModal").modal("show");
		
		$("#editNodeId").val(node.data.id);
		$("#editNodeTextField").val(node.data.name);
		$("#editNodeTextArea").val(node.data.description);
	}

	$("#copyNodeModal").on('show.bs.modal',function(e){
		$('#nodeUrl').attr('checked','checked');
		$('#nodeMethodType').attr('checked','checked');
		$('#nodeBody').attr('checked','checked');
		$('#nodeHeaders').attr('checked','checked');
		$('#nodeAuth').attr('checked','checked');
        $('#nodeTags').attr('checked','checked');

	});

	function copyNode(node){
		if (node == null) {
			alert("Please select a node to copy.");
			return;
		}
		
		$("#copyNodeId").val(node.data.id);
		$("#copyNodeTextField").val("Copy of " + node.data.name);
		$("#copyNodeType").val(node.data.nodeType);
		$("#copyNodeTextArea").val(node.data.description);
		$("#copyNodeModal").modal("show");
		
	}

	function runNode(node,event){
		if (node == null) {
			alert("Please select a node to run.");
			return;
		}
        $("#apiReqNodeId").html(node.data.id);
		APP.Events.trigger(StarEvent.CLICK,node.data.id);
				var node = new NodeModel({
					id : node.data.id
		});
	    node.fetch({
			success : function(response) {
			  if(response.get("starred")){
				$('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Unstar');
			  }else{
				$('#starNodeBtn').html('<span class="glyphicon glyphicon-star"></span>&nbsp;Star');
			  }
			  var conversation = new ConversationModel(response.get("conversation"));
			  conversation.set("id", conversation.get("id"));
			  conversation.set("name", response.get("name"));
			  conversation.set("description",response.get("description"));
									
              APP.conversation.render(conversation);
			  APP.tagsLabel.display(response.get('tags'));
			  ConversationEvents.triggerChange(response.get("conversation") ? response.get("conversation").id : null);
			  $("#run").click();
			}
		});
		
	}
	
	function deleteNode(node){
		$("#deleteNodeId").val(node.data.id);
		$("#deleteNodeModal").modal("show");
	}
	
	function nodeMenuEventHandler(event){
		event.stopPropagation();

		var currentElm = $(event.currentTarget);

		if(currentElm.hasClass('open')){
			$('.btn-group').removeClass('open');
			currentElm.removeClass('open');
		}else{
			var liElm = $(currentElm.closest('.fancytree-node'));
			
			$(liElm.parent().parent()).css("overflow","");
			$('ul.fancytree-container').css("overflow","visible");
			
			$('.btn-group').removeClass('open');
			currentElm.addClass('open');
			
			var rect = event.currentTarget.getBoundingClientRect();
			currentElm.children("ul").css({"position": "fixed", "left":rect.right , "top": rect.bottom});
		}
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


	$('#newSocketForm').validate({
		messages : {
			socketName : "Socket name is empty"
		}
	});
	
	$("#newSocketForm").submit(function(e) {
		e.preventDefault();
	});

	$("#socketModal").on('show.bs.modal',function(e){
		$('#newSocketForm .error').text('');
		$("#socketName").val("");
		$("#socketTextArea").val("");
	});

	$("#createNewSocketBtn").bind("click", function() {
	  if($('#newSocketForm').valid()){
		var conversation = null;

	    var rfRequest = {
			apiUrl : $("#apiUrl").val(),
			apiBody : APP.conversation.apiBodyCodeMirror.getValue(),
			methodType : $(".apiRequestType").val()
		};
			
		var rfResponse = {};
		conversation = new ConversationModel({
			id : null,
			rfRequestDTO : rfRequest,
			rfResponseDTO : rfResponse
		});

		tree.createNewNode({
			nodeName : $("#newSocketName").val(),
			nodeDesc : $("#socketTextArea").val(),
			conversation : conversation,
			nodeType : "SOCKET",
			successCallBack : function() {
				$("#socketModal").modal("hide");
				$("#socketModal").find("#newSocketName").val("");
				$("#socketModal").find("#socketTextArea").val("");
			}
		});
       }
	});	

	$('#newRequestForm').validate({
		messages : {
			newRequestName : "Request name is empty"
		}
	});
	
	$("#newRequestForm").submit(function(e) {
		e.preventDefault();
	});

	$("#requestModal").on('show.bs.modal',function(e){
		$('#newRequestForm .error').text('');
		$("#requestName").val("");
		$("#requestTextArea").val("");
	});


	$("#createNewRequestBtn").bind("click", function() {
	  if($('#newRequestForm').valid()){
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
       }
	});

	$('#newFolderCreationForm').validate({
		messages : {
			folderName : "Folder name is empty"
		}
	});
	$("#newFolderCreationForm").submit(function(e) {
		e.preventDefault();
	});

	$("#folderModal").on('show.bs.modal',function(e){
		$('#newFolderCreationForm .error').text('');
		$("#folderId").val("");
		$("#folderTextArea").val("");
	});

	$("#createNewFolderBtn").bind("click", function() {
	  if($("#newFolderCreationForm").valid()){
       
			tree.createNewNode({
				nodeName : $("#folderId").val(),
				nodeDesc : $("#folderTextArea").val(),
				conversation : null,
				successCallBack : function() {
					$("#folderModal").modal("hide");
				}
			});
	    }
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

	$('#createNewEntityForm').validate({
		messages : {
			entityName : "Entity name is empty"
		}
	});
	$("#createNewEntityForm").submit(function(e) {
		e.preventDefault();
	});

	
	$('#newEntityName').keyup(function() {
	    if($('#newEntityName').val() == ''){
	    	$('#new-entity-error').remove();
	    };
    });

	$("#entityModal").on('show.bs.modal',function(e){
		$("#new-entity-error").text("");
		$('#createNewEntityForm .error').text('');
		$("#newEntityName").val("");
		$("#newEntityDescription").val("");
	});

	$("#createNewEntityBtn").unbind("click").bind("click", function() {
	  if($('#createNewEntityForm').valid()){
	  	var activeFolder = tree.getActiveFolder();
	 
	    if(activeFolder.data.nodeType != 'ENTITY'){
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
        }else{
        	$('#newEntityName').after('<label class="text-danger" id="new-entity-error">Sorry you cannot create entity inside another entity.</label>');
     	}
	  }
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
		
		$("#editNodeId").val(node.data.id);
		$("#editNodeTextField").val(node.data.name);
		$("#editNodeTextArea").val(node.data.description);

	});

	$('#editNodeForm').validate({
		messages : {
			newRequestName : "Enitity name is empty"
		}
	});
	$("#editNodeForm").submit(function(e) {
		e.preventDefault();
	});

	$("#editNodeModal").on('show.bs.modal',function(e){
		$('#editNodeForm .error').text('');
	});

	$("#copyNodeBtn").unbind("click").bind("click",function(event){
		if(!$("#copyNodeType").val()){
		var nodeId = $("#copyNodeId").val();
		var node = treeObj.getNodeByKey(nodeId);
		var node = new NodeModel({
			id : node.data.id
		});
		node.fetch({
		    success : function(response) {
				var conversation = new ConversationModel(response.get("conversation"));
				var rfRequestObj = conversation.get('rfRequest');
                var tagsArray = response.get('tags');
                var headers = $('#nodeHeaders').attr('checked') == 'checked' ? rfRequestObj['rfHeaders'] : null;
                if(headers != null && headers.length >0) {
                    for(var i = 0; i < headers.length; i++){
                        headers[i].headerValue = headers[i].headerValueString;
                    }
                }
				var rfRequest = {
				    apiUrl : $('#nodeUrl').attr('checked') == 'checked' ? rfRequestObj['apiUrlString'] : '',
				    apiBody : $('#nodeBody').attr('checked') == 'checked' ? rfRequestObj['apiBodyString'] : '',
				    methodType : $('#nodeMethodType').attr('checked') == 'checked' ? rfRequestObj['methodType'] : '',
				    headers : headers,
                    basicAuthDTO : $('#nodeAuth').attr('checked') == 'checked' ? rfRequestObj['basicAuth'] : null,
                    digestAuthDTO : $('#nodeAuth').attr('checked') == 'checked' ? rfRequestObj['digestAuth'] : null
                    //ToDo: add auth
                    
			    };
			    var rfResponse = {};
			    conversation = new ConversationModel({
					id : null,
					rfRequestDTO : rfRequest,
					rfResponseDTO : rfResponse
			    });
			    var nodeId = $("#copyNodeId").val();
			    var node = treeObj.getNodeByKey(nodeId);
                var tags=[];
                tags = $('#nodeTags').attr('checked') == 'checked' ? tagsArray : null;
                
                tree.createNewNode({   
					nodeName : $("#copyNodeTextField").val(),
					nodeDesc : $("#copyNodeTextArea").val(),
					conversation : conversation,
                    tags : tags,
					parentNodeId : node.parent.data.id,
					successCallBack : function() {
						$("#copyNodeModal").modal("hide");
						$("#copyNodeModal").find("#copyNodeTextField").val("");
						$("#requestModal").find("#copyNodeTextArea").val("");
					}
				});
		    }
		});
		
		}
		
	})
	
	$("#editNodeBtn").unbind("click").bind("click", function(event) {
		var nodeId = $("#editNodeId").val();
		var node = treeObj.getNodeByKey(nodeId);
		
		var nodeModel = new NodeModel({
			id : node.data.id,
			name : $("#editNodeTextField").val(),
			description : $("#editNodeTextArea").val(),
			method : node.data.method,
			tags : []
		});

		node.data.id = nodeModel.attributes.id;
		node.data.name = nodeModel.attributes.name;
		node.data.description = nodeModel.attributes.description;
		var colorCode = getColorCode(nodeModel.attributes.method);
		var treeNodeView = new TreeNodeView();
		node.setTitle('<span class="lozenge left '+ colorCode +' auth_required">'+nodeModel.attributes.method+'</span>' +'<span class = "large-text" title = ' + nodeModel.attributes.name+'>' + nodeModel.attributes.name + '</span>' + treeNodeView.template());
		node.li.getElementsByClassName("edit-node")[0].addEventListener("click", function(){editNode(node);});
		node.li.getElementsByClassName("copy-node")[0].addEventListener("click", function(){copyNode(node);});
		node.li.getElementsByClassName("run-node")[0].addEventListener("click", function(event){runNode(node,event);});
		node.li.getElementsByClassName("menu-arrow")[0].addEventListener("click", nodeMenuEventHandler);
		nodeModel.save(null, {
			success : function(response) {
				if($("#apiReqNodeId").text() == response.get('id')){
					var node = new NodeModel({
								id : response.get('id')
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
									$("#apiReqNodeId").html(response.get('id'));
									conversation.set("id", conversation.get("id"));
									conversation.set("name", response.get("name"));
									conversation.set("description",response.get("description"));
								APP.conversation.render(conversation);
								ConversationEvents.triggerChange(response
									.get("conversation") ? response
									.get("conversation").id : null);
							}
						});
				}
				$("#editNodeTextField").val("");
				$("#editNodeTextArea").val("");
				$("#editNodeModal").modal("hide");
			},
			error : function(e) {
				alert('Some unexpected error occured Please try later.');
			}
		});
	});
        
	// API Name in second column should get trimmed based on column width and not be limited to the fixed width
	var setAPICallNameMaxWidth  = function() {
		$( ".large-text" ).each(function() {
			var fancytreeNodeWidth = $(this).closest(".fancytree-node").width();
			var fancytreeExpanderWidth = $(this).closest(".fancytree-node").find(".fancytree-expander").outerWidth(true);
			var fancytreeIconWidth = $(this).closest(".fancytree-node").find(".fancytree-icon").outerWidth(true);

			var fancytreeTitle = $(this).closest(".fancytree-title");
			var fancytreeTitleMarginsPaddings = parseInt(fancytreeTitle.css("margin-left").replace("px", "")) + parseInt(fancytreeTitle.css("margin-right").replace("px", "")) + fancytreeTitle.outerWidth() - fancytreeTitle.width();

			var lozengeWidth = $(this).closest(".fancytree-node").find(".fancytree-title").find(".lozenge").outerWidth(true);

			// menu arrow's width is hardcoded, because it is inconvenient to get it in runtime
			var menuArrowWidth = 24;

			var result = fancytreeNodeWidth - fancytreeExpanderWidth - fancytreeIconWidth - fancytreeTitleMarginsPaddings - lozengeWidth - menuArrowWidth;

			var div = $(".rf-col-2").get(0);
				if (div.scrollHeight > div.clientHeight) {
				// scroll width is hardcoded, because it is inconvenient to get it in runtime
				var scrollSize = 17;
				result -= scrollSize;
			}
			result = Math.floor(result);

			$(this).css({"max-width":result + "px"});
		});
	}

	$(window).resize(function() {
		setAPICallNameMaxWidth();
	});

$("#deleteRequestBtn").bind("click", function() {
	var nodeId = $("#deleteNodeId").val();
	var node = treeObj.getNodeByKey(nodeId);
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
			var tagView = new TagView();
			tagView.showTags();
			var tagsView = new TagsView();
			tagsView.showTags();
			var node = new NodeModel({
				id : APP.appView.getCurrentRequestNodeId()
			});
		    node.fetch({
				success : function(response) {
					APP.tagsLabel.display(response.get('tags'));
					$("#deleteTagModal").modal('hide');
				}
			});	
			

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
			socket : "glyphicon glyphicon-flash",
			socketOpen : "glyphicon glyphicon-flash",
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
						var editNodeBtn = data.node.li.getElementsByClassName("edit-node");
						var copyNodeBtn = data.node.li.getElementsByClassName("copy-node");
						var runNodeBtn = data.node.li.getElementsByClassName("run-node");
						if(editNodeBtn && editNodeBtn.length > 0){
							editNodeBtn[0].addEventListener("click", function(){editNode(data.node);});
							copyNodeBtn[0].addEventListener("click", function(){copyNode(data.node);});
							runNodeBtn[0].addEventListener("click", function(){runNode(data.node);});
						}
						var deleteNodeBtn = data.node.li.getElementsByClassName("delete-node");
						if(deleteNodeBtn && deleteNodeBtn.length > 0){
							deleteNodeBtn[0].addEventListener("click", function(){deleteNode(data.node);});
						}
					},
					click : function(event, data) {
						if (!data.node.isFolder() && data.node.data.id) {
							if(data.node.data.nodeType == "SOCKET"){
								$("#socketName").html(data.node.data.name + '<i class="fa fa-pencil edit-pencil" id="socketNameEdit"></i>');
								$("#socketDescription").html(data.node.data.description);
								$('#socketNodeId').val(data.node.data.id);
								APP.conversation.$el.hide();
								APP.projectRunner.$el.hide();
								APP.socketConnector.$el.show();
							}else{
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
										$("#apiReqNodeId").html(data.node.data.id);
										$("#rfRequestId").html(conversation.get("rfRequest").id);
                                        conversation.set("id", conversation.get("id"));
										conversation.set("name", response.get("name"));
										conversation.set("description",response.get("description"));
									// var conversationView = new
									// app.ConversationView({model : conversation});
									APP.conversation.render(conversation);
									APP.tagsLabel.display(response.get('tags'));
									ConversationEvents.triggerChange(response
										.get("conversation") ? response
										.get("conversation").id : null);
								}
							});
							}
							

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
		uiNode.key = serverNode.id;
		uiNode.name = serverNode.name;
		uiNode.method = serverNode.method;
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
			var colorCode = "";
			var title = "";

			if(serverNode.children[i].method){
				colorCode = getColorCode(serverNode.children[i].method);
				title = '<span class="lozenge left '+ colorCode +' auth_required">'+serverNode.children[i].method+'</span>' + '<span class = "large-text" title = "' + serverNode.children[i].name+'">' + serverNode.children[i].name + '</span>'+ treeNodeView.template()
			}else{
				title = serverNode.children[i].name + treeNodeView.template()
			}
			uiNode.children.push({
				title : title,
				id : serverNode.children[i].id,
				key : serverNode.children[i].id,
				name : serverNode.children[i].name,
				method : serverNode.children[i].method,
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
	 	} else if(params.nodeType == "SOCKET"){
	 		params.conversation.save(null, {
	 			success : function(response) {
	 				createNode(params.nodeName, params.nodeDesc, "SOCKET", new ConversationModel({
	 					id : response.get("id")
	 				}), null, params.successCallBack);
	 			}
	 		});

	 	}else{
	 		params.conversation.save(null, {
	 			success : function(response) {
	 				createNode(params.nodeName, params.nodeDesc, null, new ConversationModel({
	 					id : response.get("id")
	 				}), null, params.successCallBack, params.parentNodeId, params.tags);
	 			}
	 		});
	 	}
	 };


	 var createNode = function(nodeName, nodeDesc, nodeType, conversation, entity, successCallBack, parentId, tags) {
	 	var parentNodeId;
	 	var activeFolder;
	 	if(parentId){ 
           parentNodeId = parentId;
           activeFolder = treeObj.getNodeByKey(parentId);
	 	}else{
	 		activeFolder = tree.getActiveFolder();
	 	    parentNodeId = activeFolder.data.id;
	 	}
        
        if(typeof tags == "undefined"){
            tags = [];
        }
	 	
	 	var node = new NodeModel({
	 		parentId : parentNodeId,
	 		name : nodeName,
	 		description : nodeDesc,
	 		projectId : APP.appView.getCurrentProjectId(),
	 		conversationDTO : conversation,
	 		genericEntityDTO : entity,
	 		nodeType : nodeType,
            tags : tags
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

	tree.updateTreeNode = function(){
      if($('#apiRequestNameTextBox').val()){
      	var nodeId = $("#apiReqNodeId").text();
    	var node = treeObj.getNodeByKey(nodeId);
    
    	var nodeModel = new NodeModel({
      		id : node.data.id,
      		name : $('#apiRequestNameTextBox').val(),
      		method : node.data.method,
            tags : []
    	});

    	node.data.id = nodeModel.attributes.id;
    	node.data.name = nodeModel.attributes.name;
        var colorCode = getColorCode(nodeModel.attributes.method);
    	var treeNodeView = new TreeNodeView();
    	node.setTitle('<span class="lozenge left '+ colorCode +' auth_required">'+nodeModel.attributes.method+'</span>' + '<span class = "large-text" title = ' + nodeModel.attributes.name+'>' + nodeModel.attributes.name + '</span>'+ treeNodeView.template());
    	node.li.getElementsByClassName("edit-node")[0].addEventListener("click", function(){editNode(node);});
    	node.li.getElementsByClassName("copy-node")[0].addEventListener("click", function(){copyNode(node);});
    	node.li.getElementsByClassName("run-node")[0].addEventListener("click", function(event){runNode(node,event);});
    	node.li.getElementsByClassName("menu-arrow")[0].addEventListener("click", nodeMenuEventHandler);
    	nodeModel.save(null, {
      		success : function(response) {
       			$('#apiRequestNameTextBox').hide();
       			$('#apiRequestName').html($('#apiRequestNameTextBox').val() + '<i class = "fa fa-pencil edit-pencil" id ="apiRequestNameEdit"></i>');
       			$('#apiRequestName').show();
     		},
      		error : function(e) {
        		alert('Some unexpected error occured Please try later.');
      		}
    	});
      }else{
      	$('#apiRequestNameTextBox').hide();
       	$('#apiRequestName').show();
      }
	};

	tree.updateSocketTreeNode = function(){

	    var nodeId = $("#socketNodeId").val();
	    var node = treeObj.getNodeByKey(nodeId);
	    
	    var nodeModel = new NodeModel({
	      id : node.data.id,
	      name : $('#socketNameTextBox').val(),
	      method : node.data.method,
	      tags : []
	    });

	    node.data.id = nodeModel.attributes.id;
	    node.data.name = nodeModel.attributes.name;
	    var colorCode = getColorCode(nodeModel.attributes.method);
	    var treeNodeView = new TreeNodeView();
	    node.setTitle('<span class="lozenge left '+ colorCode +' auth_required">'+nodeModel.attributes.method+'</span>' + '<span class = "large-text" title = ' + nodeModel.attributes.name+'>' + nodeModel.attributes.name + '</span>'+ treeNodeView.template());
	    node.li.getElementsByClassName("edit-node")[0].addEventListener("click", function(){editNode(node);});
	    node.li.getElementsByClassName("copy-node")[0].addEventListener("click", function(){copyNode(node);});
	    node.li.getElementsByClassName("run-node")[0].addEventListener("click", function(event){runNode(node,event);});
	    node.li.getElementsByClassName("menu-arrow")[0].addEventListener("click", nodeMenuEventHandler);
	    nodeModel.save(null, {
	      success : function(response) {
	       $('#socketNameTextBox').hide();
	       $('#socketName').html($('#socketNameTextBox').val() + '<i class = "fa fa-pencil edit-pencil" id ="socketNameEdit"></i>');
	       $('#socketName').show();
	      },
	      error : function(e) {
	        alert('Some unexpected error occured Please try later.');
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
				folder : nodeModel.get('nodeType') == 'FOLDER' ? true : false,
				data : { "description" : nodeModel.get('description')}
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
				treeData = serviceSideTreeData;
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

				$('.menu-arrow').unbind("click").bind("click", nodeMenuEventHandler);
				setAPICallNameMaxWidth();
			}
		});
	};

	return tree;
});