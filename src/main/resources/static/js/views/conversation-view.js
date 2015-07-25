define(function(require) {

	"use strict";

	var Backbone = require('backbone');
	var _ = require('underscore');
	var ConversationModel = require("models/conversation");
	var AssertView = require('views/assert-view');
	var TreeView = require('views/tree-view');
	var ManageEnvironmentView = require('views/environment-view');
	var Environments = require('collections/environments');

	require('libs/prettify/prettify');
	require('typeahead');
	var lastResponse;
	var CodeMirror = require('codemirror/lib/codemirror');
	var cmjs = require('codemirror/mode/javascript/javascript');
	var imageTypes = [ 'image/png', 'image/gif', 'image/jpeg', 'image/bmp', 'image/tiff', 'image/svg+xml', 'image/webp' ];

	$(".rf-col-3").mCustomScrollbar({
		theme : "minimal-dark"
	});

	// API URLS
	var apiUrls = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('name'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : '/api/requests/api-urls',
			// the json file contains an array of strings, but the Bloodhound
			// suggestion engine expects JavaScript objects so this converts all
			// of
			// those strings
			filter : function(list) {
				return $.map(list, function(url) {
					return {
						name : url
					};
				});
			}
		}
	});
	apiUrls.initialize();

	// TODO : Disabled URL autosuggest for now. Not working properly.
	$('#apiUrl').typeahead(null, {
		name : 'apiUrls',
		displayKey : 'name',
		limit : 10,
		source : apiUrls.ttAdapter()
	});

	// HTTP HEADERS
	var httpHeaders = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('name'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : '/api/requests/http-headers',
			filter : function(list) {
				return $.map(list, function(httpHeader) {
					return {
						name : httpHeader
					};
				});
			}
		}
	});
	httpHeaders.initialize();

	$('.http-header').typeahead(null, {
		name : 'httpHeaders',
		displayKey : 'name',
		limit : 10,
		source : httpHeaders.ttAdapter()
	});

	$("#requestToggle").unbind("click").bind("click", function() {
		APP.conversation.toggleRequestSection();
	});

	$("#responseToggle").unbind("click").bind("click", function() {
		$("#responseContainer").toggle();
		if ($("#responseToggle").hasClass('glyphicon-chevron-down')) {
			$("#responseToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
		} else {
			$("#responseToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
		}
	});

	var tabSelection = function(index, tabPaneId) {
		$(".reponse-pannel > .active").removeClass("active")
		$(".reponse-pannel-ul > .active").removeClass('active');
		$('.reponse-pannel-ul li:eq(' + index + ')').addClass('active');
		$('#' + tabPaneId).addClass('active in');
	};

	$('.reponse-pannel-ul li').filter(':lt(3)').hide();
	tabSelection(3, "tab-query");

	var lastSel = $(".environmentsSelectBox option:selected");

	$('.environmentsSelectBox').unbind("change").bind("change", function(event) {
		var selctedEnv = $('.environmentsSelectBox').val();
		if (selctedEnv === 'manage-env') {
			lastSel.attr("selected", true);
			var manageEnvironmentView = new ManageEnvironmentView();
			$("#manageEnvironmentWrapper").html("");
			$("#manageEnvironmentWrapper").append(manageEnvironmentView.render().el);
			$('#manageEnvironmentsModal').modal('show');
		}
	});

	$(".environmentsSelectBox").click(function() {
		lastSel = $(".environmentsSelectBox option:selected");
	});

	$(".apiRequestType").unbind("change").bind("change", function() {
		var selectedVal = $(".apiRequestType").val();
		console.log("selectedVal = " + selectedVal);
		if ("GET" == selectedVal) {
			// TODO : Hide Tabs - RAW, FORM and FILES
			// $('#requestContainer li:eq(3) a').tab('show');
			$('.reponse-pannel-ul li').filter(':lt(3)').hide();
			tabSelection(3, "tab-query", "hide");
		} else {
			// TODO : Show Tabs - RAW, FORM and FILES
			// $('#requestContainer li:eq(0) a').tab('show');
			$('.reponse-pannel-ul li').filter(':lt(3)').show();
			tabSelection(0, "tab-body", "show");
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
			name : 'httpHeaders',
			displayKey : 'name',
			source : httpHeaders.ttAdapter()
		});
	});

	$("#copyResponse").unbind("click").bind("click", function(event) {
		if ($("#response-wrapper").text()) {
			var data = $("#response-wrapper").text();
			var myWindow = window.open("data:text/html," + encodeURIComponent(data), "_blank", "width=200,height=100");
            myWindow.location.href = $("#apiUrl").val();
			myWindow.focus();
		} else {
			event.stopPropagation();
			$("#copyResponse").attr('title', 'Response is Empty').tooltip('fixTitle').data('bs.tooltip').$tip.find('.tooltip-inner').text('Response is Empty');
		}

	});
	$("#copyResponse").on('mouseleave', function() {
		$("#copyResponse").attr('title', 'Copy the response content.').tooltip('fixTitle').data('bs.tooltip').$tip.find('.tooltip-inner').text('Copy the response content.');
	})

	$("#showLastResponse").unbind("click").bind("click", function() {
		var response = JSON.parse(localStorage.getItem("lastResponse"));
		$('#responseData').val(JSON.stringify(response));
		if (response.headers && response.headers.length > 0) {
			$("#res-header-wrapper").html('');
			var contentType;
			for (var i = 0; i < response.headers.length; i++) {
				$("#res-header-wrapper").append('<tr><td>' + response.headers[i].headerName + '</td><td>' + response.headers[i].headerValue + '</td></tr>');
				if (response.headers[i].headerName === 'Content-Type') {
					contentType = response.headers[i].headerValue;
				}
			}
			if (imageTypes.indexOf(contentType) > -1) {
				$("#response-wrapper").html('<br><pre class="prettyprint">' + '<img src="data:' + contentType + ';base64,' + btoa(response.body) + '"></img>' + '</pre>');
			} else {
				$("#response-wrapper").html('<br><pre class="prettyprint">' + response.body + '</pre>');
			}
			$("body,html").animate({
				scrollTop : $('#responseContainer').offset().top
			}, "slow");
		}
		prettyPrint();
	});

	$("#clearHeader").unbind("click").bind("click", function() {
		$("#headersWrapper").html('');
	});

	$("#clearBody").unbind("click").bind("click", function() {
		APP.conversation.apiBodyCodeMirror.setValue('');
	});

	$("[name='authOptions']").change(function() {
		$('#authOptionSelected').val($(this).val());
		if ($(this).val() == 'basic') {
			$('#tab-basic-auth').show();
			$('#tab-digest-auth').hide();
			$('#tab-oauth2').hide();
		}
		if ($(this).val() == 'digest') {
			$('#tab-basic-auth').hide();
			$('#tab-digest-auth').show();
			$('#tab-oauth2').hide();
		}
		if ($(this).val() == 'oauth2') {
			$.ajax({
				url : APP.config.baseUrl + '/oauth2',
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(res) {
					if (res != undefined && res.length > 0) {
						for (var i = 0; i < res.length; i++) {
							$(".existingOAuth").append('<option value=' + res[i].id + '>' + res[i].name + '</option>');
						}
					}
				}
			});
			// }
			var oAuth2View = new OAuth2View();
			$("#oauth2Wrapper").html("");
			$("#oauth2Wrapper").append(oAuth2View.render().el);
			$('#tab-basic-auth').hide();
			$('#tab-digest-auth').hide();
			$('#tab-oauth2').show();
		}
	});

	/*
	 * $("#noAuth").on("click",function(){
	 * $("input:radio[name='authOptions']").parent().removeClass("active");
	 * $('#tab-basic-auth').hide(); $('#tab-digest-auth').hide();
	 * $('#tab-oauth2').hide(); });
	 */

	$("#clearAuth").unbind("click").bind("click", function() {
		$("input:radio[name='authOptions']").parent().removeClass("active");
		$('#tab-basic-auth').hide();
		$('#tab-digest-auth').hide();
		$('#tab-oauth2').hide();
		$("#oauthName").val('');
		$("#authorizationUrl").val('');
		$("#accessTokenUrl").val('');
		$("#accessTokenBtn").prop('disabled', false);
		$("#bAuthUsername").val('');
		$("#bAuthPassword").val('');
		$("#digestUsername").val('');
		$("#digestPassword").val('');
	});

	$("#clearRequest").unbind("click").bind("click", function() {
		$("#headersWrapper").html('');
		APP.conversation.apiBodyCodeMirror.setValue('');

		$("#bAuthUsername").val('');
		$("#bAuthPassword").val('');
	});

	$('.label-dropdown-menu').unbind("click").bind("click", function(event) {
		event.stopPropagation();
		var currentElm = $(event.currentTarget);
		var selectedLabels = currentElm.find('li input');

		for (var i = 0; i < selectedLabels.length; i++) {
			if ($(selectedLabels[i]).attr('checked') == "checked") {
				$('.' + selectedLabels[i].id).show();
			} else {
				$('.' + selectedLabels[i].id).hide();
			}
		}

	});

	$("#accessTokenBtn").unbind("click").bind("click", function() {
		var scopes = new Array();
		scopes.push($("#authScopes").val());

		var w = "600";
		var h = "400";
		var left = (screen.width / 2) - (w / 2);
		var top = 100; // (screen.height/2)-(h/2);

		var oauthWindow = window.open("", "OAUTHWINDOW", "top=" + top + ", left=" + left + ", width=" + w + ", height=" + h + "");

		var args = {
			"authorizationUrl" : $("#authorizationUrl").val(),
			"clientId" : $("#clientId").val(),
			"scopes" : $("#authScopes").val()
		};

		openWindowWithPost('POST', '/api/oauth/form', args, 'OAUTHWINDOW');
		$("#saveAuth").show();
	});

	// Note :
	// http://stackoverflow.com/questions/17793183/how-to-replace-window-open-with-a-post
	var openWindowWithPost = function(verb, url, data, target) {
		var form = document.createElement("form");
		form.action = url;
		form.method = verb;
		form.target = target || "_self";
		if (data) {
			for ( var key in data) {
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

	$("#saveOAuthBtn").unbind("click").bind("click", function() {
		var scope = [];
		scope.push($("#authScopes").val());
		var oauth2DTO = {
			name : $("#oauthName").val(),
			authorizationUrl : $("#authorizationUrl").val(),
			accessTokenUrl : $("#accessTokenUrl").val(),
			clientId : $("#clientId").val(),
			// clientSecret : $("#oauthName").val(),
			accessTokenLocation : $("#accessTokenLocation").val(),
			scopes : scope
		};
		$.ajax({
			url : APP.config.baseUrl + '/oauth2',
			type : 'post',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(oauth2DTO),
			success : function(response) {
				alert("Saved " + $("#oauthName").val() + " successfully!");
			}
		});
	});

	var QueryParamListItemView = Backbone.View.extend({
		template : _.template($('#tpl-query-param-list-item').html()),
		queryTemplate : _.template($('#tpl-query-param-item').html()),

		events : {
			'click .destroy' : 'clear',
			'blur .urlDataName,.urlDataValue' : 'addQuery'
		},

		render : function() {
			this.$el.html(this.template());
			return this;
		},

		displayQueryParams : function(data) {
			this.$el.html(this.queryTemplate({
				query : data
			}));
			return this;
		},

		clear : function() {
			this.remove();
			this.addQuery();
		},
		addQuery : function() {
			var queryString = '';
			var urlDataNames = [];
			$('.urlDataName').each(function() {
				urlDataNames.push($(this).val());
			})

			var urlDataValues = [];
			$(".urlDataValue").each(function() {
				urlDataValues.push($(this).val());
			});

			var urlDataArr = [];
			var counter = 0;
			$.each(urlDataNames, function() {
				var urlData = {};
				urlData.key = urlDataNames[counter];
				urlData.value = urlDataValues[counter];
				if (urlData.key)
					urlDataArr.push(urlData);
				counter++;
			});
			_.each(urlDataArr, function(item, index) {
				queryString += item.key + '=' + item.value;
				if (index != urlDataArr.length - 1) {
					queryString += '&';
				}
			});

			var apiUrlData = $("#apiUrl").val().split('?');
			if (queryString != "") {
				$("#apiUrl").typeahead('val', apiUrlData[0] + '?' + queryString);
			} else {
				$("#apiUrl").typeahead('val', apiUrlData[0]);
			}

			// workaround to remove un-wanted typeahead hint
			$('#apiUrl').typeahead('open');
			$("#apiUrl").typeahead('close');
		}
	});

	var FormListItemView = Backbone.View.extend({
		template : _.template($('#tpl-form-list-item').html()),

		events : {
			'click .destroy' : 'clear',
		},

		render : function() {
			this.$el.html(this.template());
			return this;
		},

		clear : function() {
			this.remove();
		}
	});

	var FileListItemView = Backbone.View.extend({
		template : _.template($('#tpl-file-list-item').html()),

		events : {
			'click .destroy' : 'clear',
		},

		render : function() {
			this.$el.html(this.template());
			return this;
		},

		clear : function() {
			this.remove();
		}
	});

	var HeaderListItemView = Backbone.View.extend({
		template : _.template($('#tpl-header-list-item').html()),

		events : {
			'click .destroy' : 'clear',
		},

		render : function() {
			this.$el.html(this.template());
			return this;
		},

		clear : function() {
			this.remove();
		}
	});

	var OAuth2View = Backbone.View.extend({
		template : _.template($('#tpl-oauth2').html()),

		events : {
			'change .existingOAuth' : 'populateOAuth2',
		},

		render : function() {
			this.$el.html(this.template());
			return this;
		},

		populateOAuth2 : function() {
			console.log("Changed the selected option" + $('.existingOAuth').val());
			var selectedValue = $('.existingOAuth').val();
			$.ajax({
				url : APP.config.baseUrl + '/oauth2/' + selectedValue,
				type : 'get',
				dataType : 'json',
				contentType : "application/json",
				success : function(res) {
					if (res != undefined) {
						$('#oauthName').val($(".existingOAuth option:selected").text());
						$('#authorizationUrl').val(res.authorizationUrl);
						$('#accessTokenUrl').val(res.accessTokenUrl);
						$('#authScopes').val(res.authScopes);
						$('#accessTokenLocation').val(res.accessTokenLocation);
						$('#accessTokenBtn').prop("disabled", true);
					}
				}
			});
		}
	});
	
	var BodyAssertResultView = Backbone.View.extend({
		template : _.template($('#assert-result-list-item').html()),
		
		tagName: 'tbody',

		render : function() {
			return this.renderAssertResult();
		},

		renderAssertResult : function(){
			this.successCount = 0;
			
			 _.each(this.model, function (assert) {
				assert.status = assert.success ? "Success" : "Failure";
				assert.iconClass = assert.success ? "success-icon" : "failure-icon";
				var templ = this.template({result : assert});
                this.$el.append(templ);
                if(assert.success)
                	this.successCount++;
			 }, this);
			 
			 
			 return this;
		},
		
		getSuccessCount : function(){
			return this.successCount;
		}
		
		
	});

	var treeObj = $("#tree").fancytree("getTree");

	var ConversationView = Backbone.View.extend({
		el : '#conversationSection',
		events : {
			"click #apiRequestNameEdit" : "convertToTextBox",
			"focusout #apiRequestNameTextBox" : "converToLabel",
			"typeahead:change #apiUrl" : "evaluateApiUrl",
			"typeahead:select #apiUrl" : "evaluateApiUrl",
			"click #addQueryParamBtn" : "addParams"
		},

		initialize : function() {
			// Load the code mirror editor.
			var apiBodyTextArea = document.getElementById("apiBody");

			this.apiBodyCodeMirror = CodeMirror.fromTextArea(apiBodyTextArea, {
				lineNumbers : true,
				lineWrapping : true,
				indent : true,
				mode : "javascript"
			});

			this.apiBodyCodeMirror.setSize('100%', '150px');

			$("#apiUrl").keyup(function(event) {
				if (event.keyCode == 13) {
					$("#run").click();
				}
			});

			$("#run").unbind('click').bind("click", function(view) {
				return function() {
					view.run.call(view);
				};
			}(this));

			this.$el.find("#saveConversationBtn").click(function(view) {
				return function() {
					view.saveOrUpdateConversation.call(view);
				};
			}(this));
		},

		run : function() {
			localStorage.setItem("lastResponse", lastResponse);
			var start_time = new Date().getTime();
			$.ajax({
				url : APP.config.baseUrl + '/processor',
				type : 'post',
				dataType : 'json',
				contentType : "application/json",
				success : function(conversation, statusText, xhr) {
					var response = conversation.rfResponseDTO;
					$('#req-time').html(conversation.duration);
					var length = response.body.length;
					$('#status-code').html(response.status);
					$('#content-size').html(length);
					lastResponse = JSON.stringify(response);
					$('#responseData').val(JSON.stringify(response));
					if (response.headers && response.headers.length > 0) {
						$("#res-header-wrapper").html('');
						var contentType;
						for (var i = 0; i < response.headers.length; i++) {
							$("#res-header-wrapper").append('<tr><td>' + response.headers[i].headerName + '</td><td>' + response.headers[i].headerValue + '</td></tr>');
							if (response.headers[i].headerName === 'Content-Type') {
								contentType = response.headers[i].headerValue;
							}
						}
						if (imageTypes.indexOf(contentType) > -1) {
							$("#response-wrapper").html('<br><pre class="prettyprint">' + '<img src="data:' + contentType + ';base64,' + btoa(response.body) + '"></img>' + '</pre>');
						} else {
							$("#response-wrapper").html('<br><pre class="prettyprint">' + response.body + '</pre>');
						}
						$("body,html").animate({
							scrollTop : $('#responseContainer').offset().top
						}, "slow");
					}
					
					var assertResults = response.assertionDTO.bodyAssertDTOs;
					if(assertResults){
						var assertView = new BodyAssertResultView({model : assertResults});
						$('#res-assert-wrapper tbody').remove();
						$('#res-assert-wrapper').append(assertView.render().el);
						var successCount = assertView.getSuccessCount();
						$('#assertResultCount').html(successCount+'/'+assertResults.length);
						var spans = $('#res-tab-assert').find('span');
						$(spans[0]).html(successCount);
						$(spans[1]).html(assertResults.length - successCount);
					}
					
					
					prettyPrint();
					// TODO : Disable toggleRequestSection for now. Codemirror
					// update issue.
					// APP.conversation.toggleRequestSection();
				},
				data : JSON.stringify(this.getProcessRequest())
			});
		},
		getProcessRequest : function() {
			var item = {	
				id : this.nodeRfRequest ? this.nodeRfRequest.id : null,
				apiUrl : encodeURI(this.$el.find("#evaluatedApiUrl").val()),
				methodType : this.$el.find(".apiRequestType").val(),
				apiBody : this.apiBodyCodeMirror.getValue(),
				headers : this.getHeaderParams(),
				urlParams : this.getUrlParams(),
				formParams : this.getFormParams(),
				basicAuthDTO : this.getBasicAuthDTO(),
				digestAuthDTO : this.getDigestAuthDTO(),
			};
			return item;
		},

		convertToTextBox : function() {
			$('#apiRequestName').hide();
			$('#apiRequestNameTextBox').show();
			$('#apiRequestNameTextBox').focus();
			$('#apiRequestNameTextBox').val($('#apiRequestName').text());
		},

		converToLabel : function() {
			TreeView.updateTreeNode();
		},

		getBasicAuthDTO : function() {
			if ($('#authOptionSelected').val() != 'basic')
				return {};
			var basicAuthDTO = {};
			basicAuthDTO.username = $("#bAuthUsername").val();
			basicAuthDTO.password = $("#bAuthPassword").val();
			return basicAuthDTO;
		},

		getDigestAuthDTO : function() {
			if ($('#authOptionSelected').val() != 'digest')
				return {};
			var digestAuthDTO = {};
			digestAuthDTO.username = $("#digestUsername").val();
			digestAuthDTO.password = $("#digestPassword").val();
			return digestAuthDTO;
		},

		getHeaderParams : function() {
			var headerNames = [];
			this.$el.find(".headerName").each(function() {
				if ($(this).hasClass('tt-input')) {
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

		getFormParams : function() {
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

		getUrlParams : function() {
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
				if (urlData.key)
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
			$("#queryParamsWrapper").html('');
			$("#headersWrapper").html('');
			$("#bAuthUsername").val('');
			$("#bAuthPassword").val('');
			$("#tab-digest-auth input:text").val('');
			$("#tab-oauth2 input:text").val('');
			$('#req-time').html('');
			$('#status-code').html('');
			$('#content-size').html('');
			$('#res-assert-wrapper tbody').remove();
			$('#assertResultCount').html('0/0');
			var spans = $('#res-tab-assert').find('span');
			$(spans[0]).html(0);
			$(spans[1]).html(0);

			this.$el.show();

			var request = conversation.get('rfRequest');
			var response = conversation.get('rfResponse');
			this.nodeRfRequest = request;

			this.$el.find("#apiRequestName").html(conversation.get('name') + '<i class = "fa fa-pencil edit-pencil" id ="apiRequestNameEdit"></i>');
			this.$el.find("#apiRequestDescription").html(conversation.get('description'));

			this.$el.find(".apiRequestType").val(request.methodType).change();
			if (request.urlParams) {
				_.each(request.urlParams, function(item, index) {
					var queryParamListItemView = new QueryParamListItemView();
					$("#queryParamsWrapper").append(queryParamListItemView.displayQueryParams(item).el);
				})
			}

			if (request.basicAuth) {
				$("#bAuthUsername").val(request.basicAuth.username);
				$("#bAuthPassword").val(request.basicAuth.password);
			}

			if (request.digestAuth) {
				$("#digestUsername").val(request.digestAuth.username);
				$("#digestPassword").val(request.digestAuth.password);
			}

			if (request.rfHeaders) {
				_.each(request.rfHeaders, function(item, index) {
					var headerListItemView = new HeaderListItemView();
					$("#headersWrapper").append(headerListItemView.render().el);
					headerListItemView.$el.find('.http-header').typeahead(null, {
						name : 'httpHeaders',
						displayKey : 'name',
						source : httpHeaders.ttAdapter()
					});
					headerListItemView.$el.find('.headerName').val(item.headerName);
					headerListItemView.$el.find('.headerValue').val(item.headerValue);
				})
			}

			this.$el.find("#apiUrl").typeahead('val', request.apiUrl).trigger('typeahead:change');

			// workaround to remove un-wanted typeahead hint
			$('#apiUrl').typeahead('open');
			$("#apiUrl").typeahead('close');

			var evaluationExp = /(\{{)(.+)(\}})/
			var apiUrlValue = this.$el.find("#apiUrl").val().split('?')[0];
			$('#evaluatedApiUrl').val(apiUrlValue);
			var matchedData = apiUrlValue.match(evaluationExp);
			if (matchedData != null && matchedData.length > 2 && matchedData[2]) {
				var environments = new Environments();
				environments.fetch({
					success : function(response) {
						var currenctEnv = _.findWhere(response.models, {
							id : $(".environmentsSelectBox").val()
						});
						var currenctEnvProperties = currenctEnv.get('properties');
						_.each(currenctEnvProperties, function(property, index) {
							if (matchedData[2] == property.propertyName) {
								var evaluatedValue = apiUrlValue.replace('{{' + property.propertyName + '}}', property.propertyValue);
								$('#evaluatedApiUrl').val(evaluatedValue);
							}
						})
					}
				})
			}
			if (request.apiBody != null) {
				this.apiBodyCodeMirror.setValue(request.apiBody);
			} else {
				this.apiBodyCodeMirror.setValue('');
			}
			
			this.$el.find('#assertCount').html(request.assertion ? request.assertion.bodyAsserts.length : 0);

			this.$el.find("#response-wrapper").html('');
		},
		saveOrUpdateConversation : function() {
			if (APP.appView.getCurrentConversationId() != null) {
				var rfRequest = {
					id : this.nodeRfRequest ? this.nodeRfRequest.id : null,
					apiUrl : this.$el.find("#apiUrl").val(),
					apiBody : this.apiBodyCodeMirror.getValue(),
					methodType : this.$el.find(".apiRequestType").val(),
					urlParams : this.getUrlParams(),
					headers : this.getHeaderParams(),
					formParams : this.getFormParams(),
					basicAuthDTO : this.getBasicAuthDTO(),
					digestAuthDTO : this.getDigestAuthDTO(),
				}
				var rfResponse = {

				}
				var conversation = new ConversationModel({
					id : APP.appView.getCurrentConversationId(),
					nodeDTO : {id:APP.appView.getCurrentRequestNodeId()}, 
					rfRequestDTO : rfRequest,
					rfResponseDTO : rfResponse

				});
				conversation.save(null, {
					success : function() {
						alert('Changes saved successfully!');
					},
					error : function() {
						alert('some error occured while saving the request');
					}
				});
			} else {
				$("#requestModal").find("#source").val("conversation");
				$("#requestModal").modal("show");
			}
		},

		toggleRequestSection : function() {
			$("#requestContainer").toggle();
			if ($("#requestToggle").hasClass('glyphicon-chevron-down')) {
				$("#requestToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
			} else {
				$("#requestToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
			}
		},

		expandRequestSection : function() {
			if ($("#requestToggle").hasClass('glyphicon-chevron-right')) {
				$("#requestToggle").removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
				$("#requestContainer").toggle();
			}
		},

		collapseRequestSection : function() {
			if ($("#requestToggle").hasClass('glyphicon-chevron-down')) {
				$("#requestToggle").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
				$("#requestContainer").toggle();
			}
		},

		handleOauthResult : function handleOauthResult(result) {
			console.log("oauth access token : " + result);
			$('#fetchedAccessToken').html("Access Token : " + result + "<br>");
			// Adding Access Token in the header
			var headerListItemView = new HeaderListItemView();
			$("#headersWrapper").append(headerListItemView.render().el);
			headerListItemView.$el.find('.http-header').typeahead(null, {
				name : 'httpHeaders',
				displayKey : 'name',
				source : httpHeaders.ttAdapter()
			});
			headerListItemView.$el.find('.headerName').val("Authorization");
			headerListItemView.$el.find('.headerValue').val("Bearer " + result);

		},

		updateParams : function(url) {
			var queryString = url.split('?')[1];
			var params = [];
			if (queryString) {
				params = queryString.replace(/\+/g, ' ').split('&');
				var paramInputRows = $("#queryParamsWrapper .row");
				if (params.length > paramInputRows.length) {
					for (var i = 0; i < (params.length - paramInputRows.length); i++) {
						this.addParams();
					}
				}
			}
			var i = 0;
			$('.urlDataName').each(function() {
				$(this).val(params[i] ? params[i++].split('=')[0] : '');
			});

			i = 0;
			$(".urlDataValue").each(function() {
				$(this).val(params[i] ? params[i++].split('=')[1] : '');
			});
		},
		addParams : function() {
			var queryParamListItemView = new QueryParamListItemView();
			$("#queryParamsWrapper").append(queryParamListItemView.render().el);
		},
		evaluateApiUrl : function(event) {
			var evaluationExp = /(\{{)(.+)(\}})/
			var apiUrlValue = event.currentTarget.value.split('?')[0];
			$('#evaluatedApiUrl').val(apiUrlValue);
			this.updateParams(event.currentTarget.value);
			var matchedData = apiUrlValue.match(evaluationExp);
			if (matchedData != null && matchedData.length > 2 && matchedData[2]) {
				var environments = new Environments();
				environments.fetch({
					success : function(response) {
						var currenctEnv = _.findWhere(response.models, {
							id : $(".environmentsSelectBox").val()
						});
						var currenctEnvProperties = currenctEnv.get('properties');
						_.each(currenctEnvProperties, function(property, index) {
							if (matchedData[2] == property.propertyName) {
								var evaluatedValue = apiUrlValue.replace('{{' + property.propertyName + '}}', property.propertyValue);
								$('#evaluatedApiUrl').val(evaluatedValue);
							}
						})
					}
				})
			}
		}

	});
	return ConversationView;
});
