define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');	
    
    $("#manageAsserts").unbind("click").bind("click", function() {
        var assertListView = new AssertListView();
        $("#manageAssertsWrapper").html("");
        $("#manageAssertsWrapper").append(assertListView.render().el); 
        var requestId = $("#rfRequestId").text();
        if(requestId != undefined && requestId != ""){
            $.ajax({
                url : APP.config.baseUrl + '/requests/'+requestId+'/asserts',
                type : 'get',
                dataType : 'json',
                contentType : "application/json",
                success : function(res) {
                    if(res != undefined && res.bodyAsserts != undefined && res.bodyAsserts.length > 0 ){
                        var asserts = res.bodyAsserts;
                        for(var i=0; i<asserts.length; i++){
                            assertListView.addAssert();
                            $(".assertPropertyName").eq(i).val(asserts[i].propertyName);
                            $(".assertCompare").eq(i).val(asserts[i].comparator);
                            $(".assertExpectedValue").eq(i).val(asserts[i].expectedValue);
                        }
                    }
                }
            });
            }
	});
    
    $("#saveAssertsBtn").unbind("click").bind("click", function() {
        var requestId = $("#rfRequestId").text();
        var assertPayload = {};
        var bodyAssertDTOs = [];
        var bodyAssertDTO = {};
        var assertCount = $(".assertRow").length;
        for(var i=0; i<assertCount ;i++){
            bodyAssertDTO = {
                propertyName : $(".assertPropertyName").eq(i).val(),
                comparator : $(".assertCompare").eq(i).val(),
                expectedValue : $(".assertExpectedValue").eq(i).val()
            };
            bodyAssertDTOs[i]=bodyAssertDTO;
        }
        assertPayload.bodyAssertDTOs = bodyAssertDTOs;
        $.ajax({
            url : APP.config.baseUrl + '/requests/'+requestId+'/asserts',
            type : 'post',
            dataType : 'json',
            contentType : "application/json",
            data : JSON.stringify(assertPayload),
            success : function(res) {
                alert('Assert added!'+res);
    			$('#assertCount').html(bodyAssertDTOs.length);
            }
        });
	});    
    
	var AssertView = Backbone.View.extend({	
        template: _.template($('#tpl-assert').html()),
        
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
	
	var AssertListView = Backbone.View.extend({
        template: _.template($('#tpl-manage-asserts').html()),
        
        events : {
            'click #addAssertBtn': 'addAssert'
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            return this;
		},
        
        addAssert : function(){
            var assertView = new AssertView();
            this.$el.find("#assertsWrapper").append(assertView.render().el);            
        }
	});
    
	return AssertListView;
});
