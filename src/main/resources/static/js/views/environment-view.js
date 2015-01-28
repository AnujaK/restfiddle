define(function(require) {	
	"use strict";
	
	var Backbone = require('backbone');
	var _ = require('underscore');
	var EnvironmentModel = require('models/environment');
    var Environments = require('collections/environments');
    
    $("#manageEnvironments").unbind("click").bind("click", function() {
        var manageEnvironmentView = new ManageEnvironmentView();
        $("#manageEnvironmentWrapper").html("");
        $("#manageEnvironmentWrapper").append(manageEnvironmentView.render().el); 
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
            if(this.model != null){
                this.$el.find(".envPropertyName").val(this.model.propertyName);
                this.$el.find(".envPropertyValue").val(this.model.propertyValue);
            }
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
            if(this.model != null){
                console.log("selected env : "+this.model);
                this.$el.find("#environmentName").val(this.model.get("name"));
                if(this.model.get("properties") != null){
                    var properties = this.model.get("properties");
                    _.each(properties, function(property, index){
                        var envFieldView = new EnvFieldView();
                        envFieldView.model = property;
                        this.$el.find("#envFieldsWrapper").append(envFieldView.render().el);  
                    },this);
                }
            }
            return this;
		},
        
        addEnvProperty : function(){
            var envFieldView = new EnvFieldView();
            this.$el.find("#envFieldsWrapper").append(envFieldView.render().el);            
        }
	});

    var ManageEnvironmentView = Backbone.View.extend({
        template: _.template($('#tpl-manage-environment').html()),
        
        events : {
            'click #addNewEnvironmentBtn': 'addEnvironment',
            'change .existingEnvironments': 'editEnvironment'
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            var environments = new Environments();
            this.$el.find(".existingEnvironments").html('<option value="-1" selected disabled>Update Existing</option>');
            var me = this;
            environments.fetch({success : function(response){  
                    $.each(response.models,function(key, value) {
                        console.log("environment "+value.get("name"));
                        me.$el.find(".existingEnvironments").append('<option value=' + value.get("id") + '>' + value.get("name") + '</option>');
                    });
                }
            });
            this.model = environments;
            return this;
		},
        
        addEnvironment : function(){
            var environmentView = new EnvironmentView();
            this.$el.find("#environmentWrapper").html("");
            this.$el.find("#environmentWrapper").append(environmentView.render().el);          
        },
        
        editEnvironment : function(){
            var option = this.$el.find(".existingEnvironments").val();
            if(option == -1){
                return;
            }
            var selectedEnvModel = this.model.get(option);
            var environmentView = new EnvironmentView();
            environmentView.model = selectedEnvModel;
            this.$el.find("#environmentWrapper").html("");
            this.$el.find("#environmentWrapper").append(environmentView.render().el);                 
        }
	});
    
	return ManageEnvironmentView;
});
