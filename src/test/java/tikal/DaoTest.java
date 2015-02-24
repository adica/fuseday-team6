package tikal;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tikal.model.Checkin;
import tikal.service.Dao;

/**
 * Created by zeev on 2/24/15.
 */
public class DaoTest {
    private final String testUserIdZeev = "testUserIdZeev";
    Dao dao = new Dao();

    @Before
    public void prepare() throws UnknownHostException {
        dao.init();
        clean();
    }
    @Test
    public void testDao(){
        Checkin checkin = new Checkin();
        checkin.setTimestamp(new Date().getTime());
        checkin.setLongitude(1000000);
        checkin.setLatitude(2000000);
        checkin.setUserId(testUserIdZeev);
        dao.persist(checkin);

        Collection<Checkin> checkins = dao.fetch(testUserIdZeev);
        TestCase.assertEquals(1, checkins.size());
    }

    @After
    public void clean(){
        dao.removeUserStats(testUserIdZeev);
    }
}
