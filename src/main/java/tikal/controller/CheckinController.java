package tikal.controller;

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

		
		return null;
	}

	private boolean isValid(Checkin checkin) {
		if(checkin.getLatitude() < 0) return false;
		if(checkin.getLongitude() < 0) return false;
		if(checkin.getUserId() == null || checkin.getUserId().equals("")) return false;
		
		return true;
	}
}
