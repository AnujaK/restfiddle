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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.restfiddle.service.auth.EntityAuthService;

@RestController
@Transactional
public class EntityDataController {
    private static final String SUCCESS = "success";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";

    Logger logger = LoggerFactory.getLogger(EntityDataController.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EntityAuthService authService;

    // Note : SORT PARAM : Specify in the sort parameter the field or fields to sort by and a value of 1 or -1 to specify an ascending or descending
    // sort respectively (http://docs.mongodb.org/manual/reference/method/cursor.sort/).
    @RequestMapping(value = "/api/{projectId}/entities/{name}/list", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getEntityDataList(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit,
	    @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "query", required = false) String query, 
	    @RequestHeader(value = "authToken", required = false) String authToken) {
	
	JSONObject authRes = authService.authorize(projectId,authToken,"USER");
	if(!authRes.getBoolean(SUCCESS)){
	    return authRes.toString(4);
	}
	
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	DBCursor cursor;
	if (query != null && !query.isEmpty()) {
	    Object queryObject = JSON.parse(query);
	    cursor = dbCollection.find((BasicDBObject) queryObject);
	} else {
	    cursor = dbCollection.find();
	}

	if (sort != null && !sort.isEmpty()) {
	    Object sortObject = JSON.parse(sort);
	    cursor.sort((BasicDBObject) sortObject);
	}

	if (limit != null && limit > 0) {
	    if (page != null && page > 0) {
		cursor.skip((page - 1) * limit);
	    }
	    cursor.limit(limit);
	}
	List<DBObject> array = cursor.toArray();
	
	if(entityName.equals("User")){
	    for(DBObject dbObject : array){
		dbObject.removeField(PASSWORD);
	    }
	}
	
	for(DBObject dbObject : array){
	    dbRefToRelation(dbObject);
	}
	String json = JSON.serialize(array);

	// Indentation
	JSONArray jsonArr = new JSONArray(json);
	return jsonArr.toString(4);
    }

    @RequestMapping(value = "/api/{projectId}/entities/{name}/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getEntityDataById(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @PathVariable("id") String entityDataId,
	    @RequestHeader(value = "authToken", required = false) String authToken) {
	
	JSONObject authRes = authService.authorize(projectId,authToken,"USER");
	if(!authRes.getBoolean(SUCCESS)){
	    return authRes.toString(4);
	}
	
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);

	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(entityDataId));

	DBObject resultObject = dbCollection.findOne(queryObject);
	
	if(resultObject == null){
	    return "Not Found";
	}
	
	if(entityName.equals("User")){
	    resultObject.removeField(PASSWORD);
	}

	dbRefToRelation(resultObject);
	String json = resultObject.toString();

	// Indentation
	JSONObject jsonObject = new JSONObject(json);
	return jsonObject.toString(4);
    }

    /**
     * [NOTE] http://stackoverflow.com/questions/25953056/how-to-access-fields-of-converted-json-object-sent-in-post-body
     */
    @RequestMapping(value = "/api/{projectId}/entities/{name}", method = RequestMethod.POST, headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody
    String createEntityData(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @RequestBody Object genericEntityDataDTO,
	    @RequestHeader(value = "authToken", required = false) String authToken) {	
	
	String data;
	if (!(genericEntityDataDTO instanceof Map)) {
	    return null;
	} else {
	    // Note : Entity data is accessible through this map.
	    Map map = (Map) genericEntityDataDTO;
	    JSONObject jsonObj = createJsonFromMap(map);
	    data = jsonObj.toString();
	}
	
	DBObject dbObject = (DBObject) JSON.parse(data);
	
	if (entityName.equals("User")) {
	    return handleUserEntityData(projectId, dbObject, true);
	}
	
	DBRef user;
	JSONObject authRes = authService.authorize(projectId,authToken,"USER");
	if(authRes.getBoolean(SUCCESS)){
	   user = (DBRef) authRes.get("user");
	} else {
	    return authRes.toString(4);
	}
	
	
	// Create a new document for the entity.
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	
	relationToDBRef(dbObject, projectId);
	
	dbObject.put("createdBy", user);
	dbObject.put("createdAt", new Date());
	
	dbCollection.save(dbObject);
	dbRefToRelation(dbObject);
	String json = dbObject.toString();

	// Indentation
	JSONObject jsonObject = new JSONObject(json);
	return jsonObject.toString(4);
    }

    @RequestMapping(value = "/api/{projectId}/entities/{name}/{uuid}", method = RequestMethod.PUT, headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody
    String updateEntityData(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName, @PathVariable("uuid") String uuid,
	    @RequestBody Object genericEntityDataDTO,
	    @RequestHeader(value = "authToken", required = false) String authToken) {
	
	DBRef user;
	JSONObject authRes = authService.authorize(projectId,authToken,"USER");
	if(authRes.getBoolean(SUCCESS)){
	   user = (DBRef) authRes.get("user");
	} else {
	    return authRes.toString(4);
	}
	
	DBObject resultObject = new BasicDBObject();
	if (genericEntityDataDTO instanceof Map) {
	    Map map = (Map) genericEntityDataDTO;
	    if (map.get("id") != null && map.get("id") instanceof String) {
		String entityDataId = (String) map.get("id");
		logger.debug("Updating Entity Data with Id " + entityDataId);
	    }
	    JSONObject uiJson = new JSONObject(map);
	    // ID is stored separately (in a different column).
	    DBObject obj = (DBObject) JSON.parse(uiJson.toString());
	    obj.removeField("_id");

	    DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	    BasicDBObject queryObject = new BasicDBObject();
	    queryObject.append("_id", new ObjectId(uuid));
	    resultObject = dbCollection.findOne(queryObject);

	    Set<String> keySet = obj.keySet();
	    for (String key : keySet) {
		resultObject.put(key, obj.get(key));
	    }
	    
	    if(entityName.equals("User")){
		DBObject loggedInUser = dbCollection.findOne(user);
		if(loggedInUser.get(USERNAME).equals(resultObject.get(USERNAME))){
		    return handleUserEntityData(projectId, resultObject, obj.containsField(PASSWORD));
		}else{
		    return new JSONObject().put(SUCCESS, false).put("msg", "unauthorized").toString(4);
		}
	    }
	    
	    relationToDBRef(resultObject, projectId);
	    
	    resultObject.put("updatedBy", user);
	    resultObject.put("updatedAt", new Date());
	    
	    dbCollection.save(resultObject);
	}
	dbRefToRelation(resultObject);
	String json = resultObject.toString();

	// Indentation
	JSONObject jsonObject = new JSONObject(json);
	return jsonObject.toString(4);
    }

    @RequestMapping(value = "/api/{projectId}/entities/{name}/{uuid}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    StatusResponse deleteEntityData(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @PathVariable("uuid") String uuid,
	    @RequestHeader(value = "authToken", required = false) String authToken) {
	

	StatusResponse res = new StatusResponse();
	
	JSONObject authRes = authService.authorize(projectId,authToken,"USER");
	if(!authRes.getBoolean(SUCCESS)){
	    res.setStatus("Unauthorized");
	    return res;
	}
	
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(uuid));
	dbCollection.remove(queryObject);

	
	res.setStatus("DELETED");

	return res;
    }

    @SuppressWarnings("unchecked")
    private JSONObject createJsonFromMap(Map map) {
	JSONObject jsonObject = new JSONObject();
	for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
	    String key = iterator.next();
	    jsonObject.put(key, map.get(key));
	}
	return jsonObject;
    }

    private JSONObject createJsonFromEntityData(GenericEntityData entityData) {
	JSONObject jsonObject = new JSONObject(entityData.getData());
	jsonObject.put("id", entityData.getId());
	jsonObject.put("version", entityData.getVersion());
	jsonObject.put("createdDate", entityData.getCreatedDate());
	jsonObject.put("lastModifiedDate", entityData.getLastModifiedDate());
	return jsonObject;
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
	return new BasicDBObject().append("_rel",new BasicDBObject().append("entity", (obj.toString()).split("_")[1]).append("_id", ((ObjectId)obj.getId()).toHexString()));
    }
    
    private void relationToDBRef(DBObject dbObject, String projectId) {
	for (String key : dbObject.keySet()) {
	    Object obj = dbObject.get(key);
	    if (obj instanceof DBObject) {
		DBObject doc = (DBObject) obj;
		if (doc.containsField("_rel")) {
		    DBObject relation = (DBObject) doc.get("_rel");
		    dbObject.put(key, new DBRef(projectId + "_" + (String) relation.get("entity"), new ObjectId((String) relation.get("_id"))));
		} else {
		    relationToDBRef(doc, projectId);
		}
	    }
	}
    }
    
    private String handleUserEntityData(String projectId, DBObject user, boolean encryptPassword) {
	JSONObject response = new JSONObject();

	if (!user.containsField(USERNAME)) {
	    response.put("msg", "username is mandotary");
	    return response.toString(4);
	}

	if (((String) user.get(USERNAME)).length() < 3) {
	    response.put("msg", "username must be more then 3 character");
	    return response.toString(4);
	}

	if (!user.containsField(PASSWORD)) {
	    response.put("msg", "password is mandotary");
	    return response.toString(4);
	}

	if (((String) user.get(PASSWORD)).length() < 3) {
	    response.put("msg", "password must be more then 3 character");
	    return response.toString(4);
	}

	DBCollection dbCollection = mongoTemplate.getCollection(projectId + "_User");

	BasicDBObject query = new BasicDBObject();
	query.append(USERNAME, user.get(USERNAME));

	DBObject existingUser = dbCollection.findOne(query);

	if (existingUser != null && !existingUser.get("_id").equals(user.get("_id"))) {
	    response.put("msg", "username already exists");
	    return response.toString(4);
	}

	if (encryptPassword) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    user.put(PASSWORD, encoder.encode((String) user.get(PASSWORD)));
	}

	relationToDBRef(user, projectId);

	dbCollection.save(user);
	user.removeField(PASSWORD);
	dbRefToRelation(user);
	String json = user.toString();

	// Indentation
	response = new JSONObject(json);
	return response.toString(4);
    }
}
