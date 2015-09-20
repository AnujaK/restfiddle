package com.restfiddle.service.auth;

import java.util.Date;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.WriteResult;

@Service
public class EntityAuthService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public DBObject authenticate(JSONObject userDTO, String projectId) throws Exception{
	
	DBCollection dbCollection = mongoTemplate.getCollection(projectId+"_User");
	
	BasicDBObject query = new BasicDBObject();
	query.append("username", userDTO.get("username"));
	
	DBObject user = dbCollection.findOne(query);
	if(user == null){
	    throw new Exception("User not found");
	}
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	BasicDBObject auth = null;
	if(encoder.matches((String)userDTO.get("password"),(String)user.get("password"))){
	    auth = new BasicDBObject();
	    auth.append("user", new DBRef(mongoTemplate.getDb(),projectId+"_User",user.get( "_id" ))).append("expireAt", new Date(System.currentTimeMillis() + 3600 * 1000));
		
	    DBCollection dbCollectionAuth = mongoTemplate.getCollection("EntityAuth");
	    dbCollectionAuth.insert(auth);
	}else{
	   throw new Exception("Invalid password");
	}
	
	return auth;
    }
    
    public boolean logout(String llt){
	
	DBCollection dbCollection = mongoTemplate.getCollection("EntityAuth");
	
	BasicDBObject queryObject = new BasicDBObject();
	queryObject.append("_id", new ObjectId(llt));
	WriteResult result = dbCollection.remove(queryObject);
	
	return result.getN() == 1;
    }
}
