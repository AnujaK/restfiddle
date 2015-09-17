package com.restfiddle.service.auth;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@Service
public class AuthService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public String authenticate(JSONObject userDTO, String projectId){
	
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_User");
	
	BasicDBObject query = new BasicDBObject();
	query.append("username", userDTO.get("username"));
	
	DBObject user = dbCollection.findOne(query);
	
	if(userDTO.get("password").equals(user.get("password"))){
	     
	}
	
	BasicDBObject auth = new BasicDBObject();
	auth.append("user", new DBRef(mongoTemplate.getDb(),projectId+"_User",user.get( "_id" ))).append("expireAt", new Date(System.currentTimeMillis() + 3600 * 1000));
	
	DBCollection dbCollectionAuth = mongoTemplate.getCollection("EntityAuth");
	List<DBObject> indexInfo =  dbCollectionAuth.getIndexInfo();
	if(indexInfo.size() == 1){
	    dbCollectionAuth.createIndex(new BasicDBObject("expireAt", 1),new BasicDBObject("expireAfterSeconds", 0));
	}
	dbCollectionAuth.insert(auth);
	
	return auth.getString("_id");
    }
}
