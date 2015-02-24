package tikal.model;

public class AdminStats {

	private int cpuUsage;
	private int memoryUsage;

	public void setCpuUsage(int cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	
	public int getCpuUsage() {
		return cpuUsage;
	}

	public void setMemoryUsage(int memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	public int getMemoryUsage() {
		return memoryUsage;
	}
}
