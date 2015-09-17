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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.json.JSONArray;
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
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.util.JSON;
import com.restfiddle.dto.StatusResponse;
import com.restfiddle.entity.GenericEntityData;
import com.restfiddle.service.auth.AuthService;

@RestController
@Transactional
public class EntitySessionController {
    Logger logger = LoggerFactory.getLogger(EntitySessionController.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/api/{projectId}/entities/logout", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getEntityDataById(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @PathVariable("id") String entityDataId) {
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);

	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(entityDataId));

	DBObject resultObject = dbCollection.findOne(queryObject);
//	dbRefToRelation(resultObject);
	String json = resultObject.toString();

	// Indentation
	JSONObject jsonObject = new JSONObject(json);
	return jsonObject.toString(4);
    }

    /**
     * [NOTE] http://stackoverflow.com/questions/25953056/how-to-access-fields-of-converted-json-object-sent-in-post-body
     */
    @RequestMapping(value = "/api/{projectId}/entities/login", method = RequestMethod.POST, headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody
    String createEntityData(@PathVariable("projectId") String projectId,
	    @RequestBody Object genericEntityDataDTO) {
	String data = "";
	if (!(genericEntityDataDTO instanceof Map)) {
	    return null;
	} else {
	    // Note : Entity data is accessible through this map.
	    Map map = (Map) genericEntityDataDTO;
	    JSONObject jsonObj = new JSONObject(map);
	    data = authService.authenticate(jsonObj, projectId);
	}
	
	
	
	return "{\"llt\":\""+data+"\"}";
    }
}
