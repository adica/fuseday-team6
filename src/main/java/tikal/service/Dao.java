package tikal.service;

import com.google.gson.Gson;
import com.mongodb.*;
import org.springframework.stereotype.Service;
import tikal.model.Checkin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import redis.clients.jedis.Jedis;

/**
 * Created by zeev on 2/24/15.
 */
@Service
public class Dao {
    private Mongo mongo;
    private DB db;
    private DBCollection table;
    private DBCollection lastUpdates;
    private Gson gson = new Gson();
    private CheckinMapper checkinMapper = new CheckinMapper();
//    private static Jedis jedis = new Jedis("localhost");
    public void persist(Checkin checkin){
//        String json = gson.toJson(checkin);
//        jedis.lpush(checkin.getUserId(), json);
        BasicDBObject doc = checkinMapper.toDBstaticObject(checkin);
        update(checkin, doc);
        table.insert(doc);

    }

    public void init() throws UnknownHostException {
        mongo = new Mongo("127.0.0.1", 27017);
//        mongo = new Mongo("localhost", 27017);
        db = mongo.getDB("fuse");
        table = db.getCollection("checkin");
        lastUpdates = db.getCollection("lastUpdates");
    }

    public Collection<Checkin> fetch(String userId){
        BasicDBObject qry = new BasicDBObject(CheckinMapper.USER_ID, userId);
        DBCursor cursor = table.find(qry);
        List checkins = new ArrayList();
        while (cursor.hasNext()){
            DBObject next = cursor.next();
            Checkin checkin = checkinMapper.toCheckin(next);
            checkins.add(checkin);
        }
        return checkins;
    }

    private void update(Checkin checkin, DBObject doc){
        if(!exists(checkin.getUserId())){
            lastUpdates.insert(doc);
            return;
        }
        BasicDBObject query = new BasicDBObject();
        query.put(CheckinMapper.USER_ID, checkin.getUserId());
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", doc);
        lastUpdates.update(query, updateObj);
    }


    public void removeUserStats(String userId){
        BasicDBObject qry = new BasicDBObject(CheckinMapper.USER_ID, userId);
        table.remove(qry);
    }

    private boolean exists(String userId){
        BasicDBObject qry = new BasicDBObject(CheckinMapper.USER_ID, userId);
        DBCursor cursor = lastUpdates.find(qry);
        return cursor.hasNext();
    }

    public List<Checkin> getLastUpdates(int num){

        List<Checkin> checkins = new ArrayList<>();
        DBCursor cursor = lastUpdates.find();
        cursor.sort(new BasicDBObject(CheckinMapper.TIMESTAMP, -1));
        for(int i = 0; i < num; i++){
            if(!cursor.hasNext())
                break;
            Checkin checkin = checkinMapper.toCheckin(cursor.next());
            checkins.add(checkin);
        }
        return checkins;
    }
}
