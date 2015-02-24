package tikal.service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tikal.model.Checkin;

public class Worker implements Runnable {
	
	private final static Logger log = LoggerFactory.getLogger(Worker.class);
	
	private BlockingDeque<Checkin> queue = new LinkedBlockingDeque<Checkin>();

	@Override
	public void run() {
		try {
			Checkin checkin = queue.poll(20, TimeUnit.SECONDS);

			if(checkin != null) {
				
				log.info(checkin.toString());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void add(Checkin checkin) {
		queue.add(checkin);
	}

}
