define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	require('libs/prettify/prettify');
    var CodeMirror = require('codemirror/lib/codemirror');
    var cmjs = require('codemirror/mode/javascript/javascript');
    
    $("#requestToggle").unbind("click").bind("click", function() {
        APP.conversation.toggleRequestSection();
	});
    
    $("#responseToggle").unbind("click").bind("click", function() {
		$("#responseContainer").toggle();
        if($("#responseToggle").hasClass('glyphicon-chevron-down')){
            $("#responseToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
        }
        else{
             $("#responseToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
        }
	});

    $(".apiRequestType").unbind("change").bind("change", function() {
        var selectedVal = $(".apiRequestType").val();
        console.log("selectedVal = "+selectedVal);
        if("GET" == selectedVal){
            //TODO : Hide Tabs - RAW, FORM and FILES
            //$('#requestContainer li:eq(3) a').tab('show');
        }
        else{
            //TODO : Show Tabs - RAW, FORM and FILES
            //$('#requestContainer li:eq(0) a').tab('show');
        }
        
	});
    
    $("#addFormDataBtn").unbind("click").bind("click", function() {
        var formListItemView = new FormListItemView();
        $("#formDataWrapper").append(formListItemView.render().el);
	});

    $("#addFileDataBtn").unbind("click").bind("click", function() {
        var fileListItemView = new FileListItemView();
        $("#fileDataWrapper").append(fileListItemView.render().el);
	});
    
    $("#addHeaderBtn").unbind("click").bind("click", function() {
        var headerListItemView = new HeaderListItemView();
        $("#headersWrapper").append(headerListItemView.render().el);
	});
    
    $("#clearHeader").unbind("click").bind("click", function() {
        $("#headersWrapper").html('');
	});
    
    $("#clearBody").unbind("click").bind("click", function() {
        APP.conversation.apiBodyCodeMirror.setValue('');
	});
    
    $("#clearAuth").unbind("click").bind("click", function() {
        $("#bAuthUsername").val('');
        $("#bAuthPassword").val('');
	});  
    
    $("#clearRequest").unbind("click").bind("click", function() {
        $("#headersWrapper").html('');
        APP.conversation.apiBodyCodeMirror.setValue('');
        
        $("#bAuthUsername").val('');
        $("#bAuthPassword").val('');
    });
    
	var FormListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-form-list-item').html()),
        
		render : function() {
            this.$el.html(this.template());
			return this;
		}
	});

	var FileListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-file-list-item').html()),
        
		render : function() {
            this.$el.html(this.template());
			return this;
		}
	});
    
	var HeaderListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-header-list-item').html()),
        
		render : function() {
            this.$el.html(this.template());
			return this;
		}
	});
    
	var ConversationView = Backbone.View.extend({
		el : '#conversationSection',
		initialize : function(){
            // Load the code mirror editor.
            var apiBodyTextArea = document.getElementById("apiBody");
            this.apiBodyCodeMirror = CodeMirror.fromTextArea(apiBodyTextArea, {
                lineNumbers: true,
                lineWrapping: true,
                indent: true,
                mode: "javascript"
            });
            
            this.apiBodyCodeMirror.setSize('100%', '150px');
            
			$("#run").unbind('click').bind("click", function(view){
				return function(){view.run.call(view);};
			}(this));
			this.$el.find("#saveConversationBtn").click(function(view){
				return function(){view.saveOrUpdateConversation.call(view);};
			}(this));
		},
		run : function(){
			$.ajax({
				url : APP.config.baseUrl + '/processor',
				type : 'post',
				dataType : 'json',
				contentType : "application/json",
				success : function(response) {
					console.log("####" + response);
					$("#response-wrapper").html('<br><pre class="prettyprint">'+ response.body+ '</pre>');
					if(response.headers && response.headers.length > 0){
                        $("#res-header-wrapper").html('');
						for(var i = 0 ; i < response.headers.length; i++){
							$("#res-header-wrapper").append('<tr><td>'+response.headers[i].headerName+'</td><td>'+response.headers[i].headerValue+'</td></tr>');
						}
					}
					prettyPrint();
                    APP.conversation.toggleRequestSection();
				},
				data : JSON.stringify(this.getProcessRequest())
			});
		},
		getProcessRequest : function(){
            var item = {
					apiUrl : this.$el.find("#apiUrl").val(),
					methodType : this.$el.find(".apiRequestType").val(),
					apiBody : this.apiBodyCodeMirror.getValue(),
                    headers : this.getHeaderParams(),
                    urlParams : this.getUrlParams(),
                    formParams : this.getFormParams()
				};
			return item;
		},
        
        getHeaderParams : function(){
            var headerNames = [];
            this.$el.find(".headerName").each(function() {
                var headerKey = {};
                headerKey.headerName = $(this).val();
                headerNames.push(headerKey);
            });  
            
            var headerValues = [];
            this.$el.find(".headerValue").each(function() {
                var headerVal = {};
                headerVal.headerValue = $(this).val();
                headerValues.push(headerVal);
            }); 

            var headerDataArr = [];
            var counter = 0;
            $.each(headerNames, function() {
                var headerData = {};
                headerData.headerName = headerNames[counter].headerName;
                headerData.headerValue = headerValues[counter].headerValue;
                headerDataArr.push(headerData);
                counter++;
            });  
            return headerDataArr;
        },

        getFormParams : function(){
            var formDataNames = [];
            this.$el.find(".formDataName").each(function() {
                var formDataKey = {};
                formDataKey.key = $(this).val();
                formDataNames.push(formDataKey);
            });  
            
            var formDataValues = [];
            this.$el.find(".formDataValue").each(function() {
                var formDataVal = {};
                formDataVal.value = $(this).val();
                formDataValues.push(formDataVal);
            }); 

            var formDataArr = [];
            var counter = 0;
            $.each(formDataNames, function() {
                var formData = {};
                formData.key = formDataNames[counter].key;
                formData.value = formDataValues[counter].value;
                formDataArr.push(formData);
                counter++;
            });  
            return formDataArr;
        },
        
        getUrlParams : function(){
            var urlDataNames = [];
            this.$el.find(".urlDataName").each(function() {
                var urlDataKey = {};
                urlDataKey.key = $(this).val();
                urlDataNames.push(urlDataKey);
            });  
            
            var urlDataValues = [];
            this.$el.find(".urlDataValue").each(function() {
                var urlDataVal = {};
                urlDataVal.value = $(this).val();
                urlDataValues.push(urlDataVal);
            }); 

            var urlDataArr = [];
            var counter = 0;
            $.each(urlDataNames, function() {
                var urlData = {};
                urlData.key = urlDataNames[counter].key;
                urlData.value = urlDataValues[counter].value;
                urlDataArr.push(urlData);
                counter++;
            });  
            return urlDataArr;
        },
        
        render : function(conversation) {
			console.log('render conversation view with model');
			console.log(conversation);
            
            APP.projectRunner.$el.hide();
            this.$el.show();
            
			var request = conversation.get('rfRequest');
			var response = conversation.get('rfResponse');
			
			this.$el.find("#apiRequestName").html(conversation.get('name'));
			this.$el.find("#apiRequestDescription").html(conversation.get('description'));	
			
			this.$el.find("#apiUrl").val(request.apiUrl);
			this.$el.find(".apiRequestType").val(request.methodType);
            if(request.apiBody != null){
                this.apiBodyCodeMirror.setValue(request.apiBody);
            }
            else{
             this.apiBodyCodeMirror.setValue('');
            }
           
			
			this.$el.find("#response-wrapper").html('');
		},
		saveOrUpdateConversation : function(){
			if(APP.appView.getCurrentConversationId() != null){
				var rfRequest = {
						apiUrl : this.$el.find("#apiUrl").val(),
						apiBody : this.apiBodyCodeMirror.getValue(),
						methodType : this.$el.find(".apiRequestType").val()
				}
				var rfResponse = {
						
				}
				var conversation = new ConversationModel({
					id : APP.appView.getCurrentConversationId(),
					rfRequestDTO : rfRequest,
					rfResponseDTO : rfResponse
					
				});
				conversation.save(null, {
					success: function(){
						alert('Changes saved successfully!');
					},
					error : function(){
						alert('some error occured while saving the request');
					}
				});
			}else{
				$("#requestModal").find("#source").val("conversation");
				$("#requestModal").modal("show");
			}
		},
        
        toggleRequestSection : function(){
            $("#requestContainer").toggle();
            if($("#requestToggle").hasClass('glyphicon-chevron-down')){
                $("#requestToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
            }
            else{
                $("#requestToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
            }
        },
        
        expandRequestSection : function(){
            if($("#requestToggle").hasClass('glyphicon-chevron-right')){
                $("#requestToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
                $("#requestContainer").toggle();
            }
        },
        
        collapseRequestSection : function(){
            if($("#requestToggle").hasClass('glyphicon-chevron-down')){
                $("#requestToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
                $("#requestContainer").toggle();
            }
        }
        
	});
	return ConversationView;
});
