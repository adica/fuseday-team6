package tikal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.model.Checkin;
import tikal.model.Status;


@RestController
public class CheckinController {
	
	@RequestMapping(value="/checkin",method=RequestMethod.POST)
	public ResponseEntity<Status> checkin(@RequestBody final Checkin checkin) {
		checkin.setTimestamp(System.currentTimeMillis());
		System.out.println(checkin);
		return new ResponseEntity<Status>(new Status(), HttpStatus.OK);
	}
}
