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

@RestController
@Transactional
public class EntityDataController {
    Logger logger = LoggerFactory.getLogger(EntityDataController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    // Note : SORT PARAM : Specify in the sort parameter the field or fields to sort by and a value of 1 or -1 to specify an ascending or descending
    // sort respectively (http://docs.mongodb.org/manual/reference/method/cursor.sort/).
    @RequestMapping(value = "/api/{projectId}/entities/{name}/list", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getEntityDataList(@PathVariable("projectId") String projectId, @PathVariable("name") String entityName,
	    @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit,
	    @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "query", required = false) String query) {
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	DBCursor cursor = null;
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
	    @PathVariable("id") String entityDataId) {
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);

	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(entityDataId));

	DBObject resultObject = dbCollection.findOne(queryObject);
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
	    @RequestBody Object genericEntityDataDTO) {
	String data = "";
	if (!(genericEntityDataDTO instanceof Map)) {
	    return null;
	} else {
	    // Note : Entity data is accessible through this map.
	    Map map = (Map) genericEntityDataDTO;
	    JSONObject jsonObj = createJsonFromMap(map);
	    data = jsonObj.toString();
	}
	// Create a new document for the entity.
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);

	DBObject dbObject = (DBObject) JSON.parse(data);
	
	relationToDBRef(dbObject, projectId);
	
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
	    @RequestBody Object genericEntityDataDTO) {
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
	    relationToDBRef(resultObject, projectId);
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
	    @PathVariable("uuid") String uuid) {
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_"+entityName);
	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(uuid));
	dbCollection.remove(queryObject);

	StatusResponse res = new StatusResponse();
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
	return new BasicDBObject().append("_rel",new BasicDBObject().append("entity", obj.getRef().split("_")[1]).append("_id", ((ObjectId)obj.getId()).toHexString()));
    }
    
    private void relationToDBRef(DBObject dbObject, String projectId) {
	for (String key : dbObject.keySet()) {
	    Object obj = dbObject.get(key);
	    if (obj instanceof DBObject) {
		DBObject doc = (DBObject) obj;
		if (doc.containsField("_rel")) {
		    DBObject relation = (DBObject) doc.get("_rel");
		    dbObject.put(key, new DBRef(mongoTemplate.getDb(), projectId + "_" + (String) relation.get("entity"), (new ObjectId((String) relation.get("_id")))));
		} else {
		    relationToDBRef(doc, projectId);
		}
	    }
	}
    }
}
