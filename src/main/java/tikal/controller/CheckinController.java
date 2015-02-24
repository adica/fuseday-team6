package tikal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.model.Checkin;
import tikal.model.Status;
import tikal.service.CheckinQueue;


@RestController
public class CheckinController {
	
	@Autowired
	private CheckinQueue checkinQueue;
	
	@RequestMapping(value="/checkin",method=RequestMethod.POST)
	public ResponseEntity<Status> checkin(@RequestBody final Checkin checkin) {
		checkin.setTimestamp(System.currentTimeMillis());

		if(isValid(checkin)) {
			checkinQueue.add(checkin);
			return new ResponseEntity<Status>(new Status(), HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>(new Status("Invalid location data!"), HttpStatus.OK);			
		}
	}
	
	@RequestMapping(value="/api/location",method=RequestMethod.GET)
	public ResponseEntity<List<Checkin>> locations() {

		Checkin c1 = new Checkin();
		c1.setLatitude(12345.23);
		c1.setLongitude(67890.23);
		c1.setUserId("hanang");
		
		Checkin c2 = new Checkin();
		c2.setLatitude(54321.32);
		c2.setLongitude(09876.32);
		c2.setUserId("oren");
		
		List<Checkin> l = new ArrayList<Checkin>();
		l.add(c1);
		l.add(c2);
		
		
		return new ResponseEntity<List<Checkin>>(l, HttpStatus.OK);
	}

	private boolean isValid(Checkin checkin) {
		if(checkin.getLatitude() < 0) return false;
		if(checkin.getLongitude() < 0) return false;
		if(checkin.getUserId() == null || checkin.getUserId().equals("")) return false;
		
		return true;
	}
}
