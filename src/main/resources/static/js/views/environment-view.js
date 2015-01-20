define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var EnvironmentModel = require('models/environment');
    var Environments = require('collections/environments');
    
    $("#manageEnvironments").unbind("click").bind("click", function() {
        var environments = new Environments();
        $(".existingEnvironments").html('<option value="-1">Update Existing</option>');
        environments.fetch({success : function(response){  
                $.each(response.models,function(key, value) {
                    console.log("environment "+value.get("name"));
                    $(".existingEnvironments").append('<option value=' + key + '>' + value.get("name") + '</option>');
                });
            }
        });
	});
    
    $("#addNewEnvironmentBtn").unbind("click").bind("click", function() {
        var environmentView = new EnvironmentView();
        $("#environmentWrapper").html("");
        $("#environmentWrapper").append(environmentView.render().el);
	});
    
	$("#saveEnvironmentBtn").unbind("click").bind("click", function() {
        var environmentName = $("#environmentName").val();
        
        var envProperties = getEnvProperties();
        
        var environmentModel = new EnvironmentModel({
            id : null,
            name : environmentName,
            properties : envProperties
            
        });
        
        environmentModel.save(null,{
            success: function(){
                alert("Environment saved successfully.");
                $("#manageEnvironmentsModal").modal("hide");
            }
        });
	});
    
    var getEnvProperties = function(){
        var propertyNames = [];
        $(".envPropertyName").each(function() {
            var propertyName = {};
            propertyName.propertyName = $(this).val();
            propertyNames.push(propertyName);
        });  

        var propertyValues = [];
        $(".envPropertyValue").each(function() {
            var propertyValue = {};
            propertyValue.propertyValue = $(this).val();
            propertyValues.push(propertyValue);
        }); 

        var propertyArr = [];
        var counter = 0;
        $.each(propertyNames, function() {
            var property = {};
            property.propertyName = propertyNames[counter].propertyName;
            property.propertyValue = propertyValues[counter].propertyValue;
            propertyArr.push(property);
            counter++;
        });  
        return propertyArr;
    };	
    
	var EnvFieldView = Backbone.View.extend({	
        template: _.template($('#tpl-env-field').html()),
        
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
	
	var EnvironmentView = Backbone.View.extend({
        template: _.template($('#tpl-environment').html()),
        
        events : {
            'click #addEnvFieldBtn': 'addEnvProperty',
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            return this;
		},
        
        addEnvProperty : function(){
            var envFieldView = new EnvFieldView();
            this.$el.find("#envFieldsWrapper").append(envFieldView.render().el);            
        }
	});
	
	return EnvironmentView;
});
