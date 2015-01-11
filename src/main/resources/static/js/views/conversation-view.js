define(function(require) {
	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	
    require('libs/prettify/prettify');
    require('typeahead');
    
    var CodeMirror = require('codemirror/lib/codemirror');
    var cmjs = require('codemirror/mode/javascript/javascript');

    //API URLS
    var apiUrls = new Bloodhound({
      datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      limit: 10,
      prefetch: {
        url: '/api/requests/api-urls',
        // the json file contains an array of strings, but the Bloodhound
        // suggestion engine expects JavaScript objects so this converts all of
        // those strings
        filter: function(list) {
          return $.map(list, function(url) { return { name: url }; });
        }
      }
    });
    apiUrls.initialize();

    //TODO : Disabled URL autosuggest for now. Not working properly.
    //$('#apiUrl').typeahead(null, {
      //name: 'apiUrls',
      //displayKey: 'name',
      //source: apiUrls.ttAdapter()
    //});
    
    //HTTP HEADERS
    var httpHeaders = new Bloodhound({
      datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      limit: 10,
      prefetch: {
        url: '/api/requests/http-headers',
        filter: function(list) {
          return $.map(list, function(httpHeader) { return { name: httpHeader }; });
        }
      }
    });
    httpHeaders.initialize();

    $('.http-header').typeahead(null, {
      name: 'httpHeaders',
      displayKey: 'name',
      source: httpHeaders.ttAdapter()
    });
    
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
        
        headerListItemView.$el.find('.http-header').typeahead(null, {
            name: 'httpHeaders',
            displayKey: 'name',
            source: httpHeaders.ttAdapter()
        });
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

    $("#accessTokenBtn").unbind("click").bind("click", function() {
        var scopes = new Array();
        scopes.push($("#authScopes").val());
        
        var w = "600";
        var h = "400";
        var left = (screen.width/2)-(w/2);
        var top = 100; //(screen.height/2)-(h/2);
        
        var oauthWindow = window.open("", "OAUTHWINDOW", "top="+top+", left="+left+", width="+w+", height="+h+"");
        
        var args  = {
            "authorizationUrl" : $("#authorizationUrl").val(),
            "clientId" : $("#clientId").val(),
            "scopes" : $("#authScopes").val()
        };
        
        openWindowWithPost('POST', '/api/oauth/form', args, 'OAUTHWINDOW');
	}); 
    
    //Note : http://stackoverflow.com/questions/17793183/how-to-replace-window-open-with-a-post
    var openWindowWithPost = function(verb, url, data, target) {
        var form = document.createElement("form");
        form.action = url;
        form.method = verb;
        form.target = target || "_self";
        if (data) {
        for (var key in data) {
          var input = document.createElement("textarea");
          input.name = key;
          input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
          form.appendChild(input);
        }
        }
        form.style.display = 'none';
        document.body.appendChild(form);
        form.submit();
    };
    
	var FormListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-form-list-item').html()),
        
        events : {
            'click .destroy': 'clear',
        },
        
		render : function() {
            this.$el.html(this.template());
			return this;
		},
        
        clear : function(){
            this.remove();
        }
	});

	var FileListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-file-list-item').html()),
        
        events : {
            'click .destroy': 'clear',
        },
        
		render : function() {
            this.$el.html(this.template());
			return this;
		},
        
        clear : function(){
            this.remove();
        }
	});
    
	var HeaderListItemView = Backbone.View.extend({	
        template: _.template($('#tpl-header-list-item').html()),
        
        events : {
            'click .destroy': 'clear',
        },
        
		render : function() {
            this.$el.html(this.template());
			return this;
		},
        
        clear : function(){
            this.remove();
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
                    //TODO : Disable toggleRequestSection for now. Codemirror update issue.
                    //APP.conversation.toggleRequestSection();
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
                if($(this).hasClass('tt-input')){
                    var headerKey = {};
                    headerKey.headerName = $(this).val();
                    headerNames.push(headerKey);
                }
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
            APP.socketConnector.$el.hide();
            APP.socketConnector.$el.hide();
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
        },
        
        handleOauthResult : function handleOauthResult(result) {
            console.log("oauth access token : " + result);
            $('#fetchedAccessToken').html("Access Token : " + result+"<br>");
        }
        
	});
	return ConversationView;
});
