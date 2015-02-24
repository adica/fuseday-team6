package tikal.model;

public class AdminStats {

	private double cpuUsage;
	private int memoryUsage;
	private int queueSize;

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	
	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setMemoryUsage(int memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	public int getMemoryUsage() {
		return memoryUsage;
	}
	
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
	
	public int getQueueSize() {
		return queueSize;
	}
}
