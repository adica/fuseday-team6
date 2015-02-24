package tikal.service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import tikal.model.Checkin;

public class Worker implements Runnable {
	
	//private final static logger
	
	private BlockingDeque<Checkin> queue = new LinkedBlockingDeque<Checkin>();

	@Override
	public void run() {
		Checkin checkin = queue.poll();
		
	}
	
	public void add(Checkin checkin) {
		queue.add(checkin);
	}

}
