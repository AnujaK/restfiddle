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
    
    $("#manageEnvironmentsModal").on('show.bs.modal',function(e){
        $("#environment-name-error").text("");
    });

    $('#environmentManagementForm').validate({
        messages : {
            tagName : "Environment name is empty"
        }
    });

    $("#environmentManagementForm").submit(function(e) {
        e.preventDefault();
    });
    
	$("#saveEnvironmentBtn").unbind("click").bind("click", function() {
      if($("#environmentManagementForm").valid()){
        var environments = new Environments();
        var that = this;
        environments.fetch({
            success :  function(response){
                that.collection = response;
                var environmentWithSameName = that.collection.findWhere({name : $("#environmentName").val()});
                if(!environmentWithSameName){
                    var environmentName = $("#environmentName").val();
        
                    var envProperties = getEnvProperties();
                    
                    var envId = $("#environmentName").data('environment-id');
                    if(envId == "-1"){
                        envId = null;
                    }
                    var environmentModel = new EnvironmentModel({
                        id : envId,
                        name : environmentName,
                        properties : envProperties
                        
                    });
                    
                    environmentModel.save(null,{
                        success: function(){
                            var manageEnvironmentView = new ManageEnvironmentView();
                            manageEnvironmentView.render();
                            $("#manageEnvironmentsModal").modal("hide");
                        }
                    });

                }else{
                    $('#environmentName').after('<label class="text-danger" id="environment-name-error">Environment name already exists</label>');
                }
            }
          });
       }
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
        initialize : function() {
        },
        template: _.template($('#tpl-environment').html()),
        
        events : {
            'click #addEnvFieldBtn': 'addEnvProperty',
        },

		render : function() {
            this.$el.html(this.template());
            if(this.model != null){
                console.log("selected env : "+this.model);
                this.$el.find("#environmentName").val(this.model.get("name"));
                this.$el.find("#environmentName").data('environment-id', this.model.get("id"));
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
            'change .existingEnvironments': 'editEnvironment',
            'click #deleteEnvironment'  : 'deleteEnvironment',
            'keyup #environmentName'    : 'removeError'
        },
        
		initialize : function() {
		},
		
		render : function() {
            this.$el.html(this.template());
            var environments = new Environments();
            this.$el.find(".existingEnvironments").html('<option value="-1" selected disabled>Update Existing</option>');
            var me = this;
           
            environments.fetch({
                success : function(response){
                    $(".environmentsSelectBox").empty();
                   if(response.models.length == 0){
                    $(".environmentsSelectBox").append('<option>Select</option>');
                   }  
                    $.each(response.models,function(key, value) {
                        console.log("environment "+value.get("name"));
                        me.$el.find(".existingEnvironments").append('<option value=' + value.get("id") + '>' + value.get("name") + '</option>');
                        $(".environmentsSelectBox").append('<option value=' + value.get("id") + '>' + value.get("name") + '</option>');
                    });
                    $(".environmentsSelectBox").append('<option value = "manage-env">Manage Environments</option>');
                }
            });
            
            this.model = environments;
            return this;
		},

        removeError : function() {
            if($('#environmentName').val() == ''){
                $('#environment-name-error').remove();
            };
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
            this.$el.find("#deleteEnvironment").show();
        },

        deleteEnvironment : function(){
            $.ajax({
                url : APP.config.baseUrl + '/environments/' + $('#environmentName').data('environment-id'),
                type : 'delete',
                dataType : 'json',
                contentType : "application/json",
                success : function(data) {
                    $('.existingEnvironments').val('-1').change();
                    $(".existingEnvironments option[value='"+ $('#environmentName').data('environment-id')+"']").remove();
                    var manageEnvironmentView = new ManageEnvironmentView();
                    manageEnvironmentView.render();
                    $('#addNewEnvironmentBtn').click();
                }
            });
        }
	});
    
	return ManageEnvironmentView;
});
