package tikal.service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.springframework.stereotype.Service;

@Service
public class PerformanceService {

	public double getCpu() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

		return operatingSystemMXBean.getSystemLoadAverage();
	}

	public int getMemory() {
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		long usedMemory = totalMemory - freeMemory;

		float mem = usedMemory / (float) totalMemory * 100;
		return (int) mem;
	}
}
