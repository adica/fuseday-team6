package tikal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import tikal.model.Checkin;

@Service
public class CheckinQueue {

	private static final int WORKERS_SIZE = 5;
	private final BlockingDeque<Checkin> queue = new LinkedBlockingDeque<Checkin>();
	private final List<Worker> workers = new ArrayList<Worker>();
	

	@PostConstruct
	public void setup() {
		for (int i = 0; i < WORKERS_SIZE; i++) {
			Worker worker = new Worker();
			new Thread(worker).start();
			workers.add(worker);
		}
		
		new Thread(new Poller()).start();
	}
	
	public void add(Checkin checkin) {
		queue.add(checkin);
	}

	public int pendingChecking() {
		return queue.size();
	}

	/**
	 * Hand to thread in Round Robin manner.
	 * @author hanang
	 *
	 */
	private class Poller implements Runnable {
		
		private int index = 0;

		@Override
		public void run() {
			while (true) {
				try {
					Checkin checkin = queue.poll(20, TimeUnit.SECONDS);
					if(checkin != null) {
						workers.get(index++).add(checkin);
						
						if(index == workers.size()) {
							index = 0;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

}
