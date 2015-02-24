package tikal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.model.AdminStats;


@RestController
public class AdminController {

	@RequestMapping(value="/admin/stats",method=RequestMethod.GET)
	public ResponseEntity<AdminStats>  getStats() {

		AdminStats stat = new AdminStats();
		stat.setCpuUsage(5);
		stat.setMemoryUsage(5);

		return new ResponseEntity<AdminStats>(stat, HttpStatus.OK);
	}
}
