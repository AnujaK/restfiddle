/*
 * Copyright 2015 Ranjan Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.restfiddle.controller.rest;

import java.util.Map;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.restfiddle.service.auth.EntityAuthService;

@RestController
@Transactional
public class EntitySessionController {
    Logger logger = LoggerFactory.getLogger(EntitySessionController.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EntityAuthService authService;

    @RequestMapping(value = "/api/{projectId}/entities/logout", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getEntityDataById(@PathVariable("projectId") String projectId,
	    @RequestParam(value = "authToken", required = true) String llt) {
	
	boolean status = authService.logout(llt);
	
	JSONObject response = new JSONObject();
	
	if(status){
	    response.put("msg", "sucessfully logged out");
	}else{
	    response.put("msg", "session expired"); 
	}
	
	return response.toString(4);
    }

    @RequestMapping(value = "/api/{projectId}/entities/login", method = RequestMethod.POST, headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody
    String createEntityData(@PathVariable("projectId") String projectId,
	    @RequestBody Object userDTO) {
	DBObject data;
	if (!(userDTO instanceof Map)) {
	    return null;
	} 
	
	JSONObject response = new JSONObject();
	
	Map map = (Map) userDTO;
	JSONObject jsonObj = new JSONObject(map);
	try {
	    data = authService.authenticate(jsonObj, projectId);
	} catch (Exception e) {
	    response.put("msg", e.getMessage());
	    return response.toString(4);
	}
	DBCollection userCollection = mongoTemplate.getCollection(projectId + "_User");
	
	DBObject user = userCollection.findOne(((DBRef)(data.get("user"))).getId());
	user.removeField("password");
	data.put("user", user);
	
	dbRefToRelation(data);
	data.put("authToken", data.get("_id"));
	data.removeField("_id");
	data.removeField("expireAt");
	String json = data.toString();

	// Indentation
	response = new JSONObject(json);
	return response.toString(4);
    }
    
    private void dbRefToRelation(DBObject dbObject) {
	if (dbObject == null) {
	    return;
	}
	if (dbObject.containsField("_id")) 
	    dbObject.put("_id", ((ObjectId) dbObject.get("_id")).toHexString());
	for (String key : dbObject.keySet()) {
	    Object obj = dbObject.get(key);
	    if (obj instanceof DBRef) {
		DBRef ref = (DBRef) obj;
		dbObject.put(key, dbRefToRel(ref));
	    } else if (obj instanceof DBObject) {
		dbRefToRelation((DBObject) obj);
	    }
	}

    }
    
    private DBObject dbRefToRel(DBRef obj){
	return new BasicDBObject().append("_rel",new BasicDBObject().append("entity", ((String) obj.getId()).split("_")[1]).append("_id", ((ObjectId)obj.getId()).toHexString()));
    }

}
