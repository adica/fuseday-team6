package tikal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.model.AdminStats;
import tikal.service.CheckinQueue;


@RestController
public class AdminController {
	
	@Autowired
	private CheckinQueue checkinQueue;

	@RequestMapping(value="/admin/stats",method=RequestMethod.GET)
	public ResponseEntity<AdminStats>  getStats() {

		AdminStats stat = new AdminStats();
		stat.setCpuUsage(5);
		stat.setMemoryUsage(5);
		stat.setQueueSize(checkinQueue.pendingChecking());

		return new ResponseEntity<AdminStats>(stat, HttpStatus.OK);
	}
}
