define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
    var TreeView = require('views/tree-view');
   
    var wsUri; 
    var webSocket;
    var messageToSend;
    var output;
    
    $("#btnConnectSocket").unbind("click").bind("click", function() {
        wsUri = APP.socketConnector.$el.find('#secketUrl').val();
        webSocket = new WebSocket(wsUri);
        var webSocketResponseBody = APP.socketConnector.$el.find('#webSocketResponseBody');
        webSocket.onopen = function(evt) {
            webSocketResponseBody.append('<p>Connected</p>');
        };
        webSocket.onclose = function(evt) { 
            webSocketResponseBody.append('<p>Disconnected</p>'); 
        }; 
        webSocket.onmessage = function(evt) {
           webSocketResponseBody.append('<p>RESPONSE: ' + evt.data+'</p>');
        }; 
        webSocket.onerror = function(evt) {
           webSocketResponseBody.append('<p>ERROR: ' + evt.data+'</p>'); 
        };

    });
    $("#btnDisconnectSocket").unbind("click").bind("click", function() {
        webSocket.close();

    });
    $("#btnSendMessage").unbind("click").bind("click", function() {
        messageToSend = APP.socketConnector.$el.find('#socketMessage').val();
        webSocket.send(messageToSend);
        
        var webSocketResponseBody = APP.socketConnector.$el.find('#webSocketResponseBody');
        webSocketResponseBody.append('<p>SENT: ' + messageToSend+'</p>');

    });
	var SocketConnectorView = Backbone.View.extend({
        el: '#webSocketSection',
        events : {
            'click #socketNameEdit' : 'convertToTextBox',
            "focusout #socketNameTextBox" : "converToLabel"

        },

        convertToTextBox : function(){
            $("#socketName").hide();
            $("#socketNameTextBox").show();
            $("#socketNameTextBox").focus();
            $("#socketNameTextBox").val($("#socketName").text());
        },
        converToLabel : function(){
            TreeView.updateSocketTreeNode();
        },
		initialize : function() {
			this.$el.hide();
		},
		
		render : function() {
            var webSocketResponseBody = this.$el.find('#webSocketResponseBody');
            
            return this;
		}
	});
	
	return SocketConnectorView;
	
});
