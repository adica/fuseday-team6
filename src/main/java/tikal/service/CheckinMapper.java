package tikal.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import tikal.model.Checkin;

/**
 * Created by zeev on 2/24/15.
 */
public class CheckinMapper {

    public static final String USER_ID = "userId";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String TIMESTAMP = "timestamp";
    public static  final String SEC = "sec";

    public BasicDBObject toDBstaticObject(Checkin checkin){
        long timestamp = checkin.getTimestamp();
        long rounded = timestamp - (timestamp % 1000);
        BasicDBObject document = new BasicDBObject();
        document.put(USER_ID, checkin.getUserId());
        document.put(LAT, checkin.getLatitude());
        document.put(LON, checkin.getLongitude());
        document.put(TIMESTAMP, timestamp);
        document.put(SEC, rounded);
        return document;

    }

    public Checkin toCheckin(DBObject doc){
        Checkin checkin = new Checkin();
        checkin.setUserId((String) doc.get(USER_ID));
        checkin.setLatitude(((Number) doc.get(LAT)).longValue());
        checkin.setLongitude(((Number) doc.get(LON)).longValue());
        checkin.setTimestamp(((Number) doc.get(TIMESTAMP)).longValue());
        return checkin;
    }
}
