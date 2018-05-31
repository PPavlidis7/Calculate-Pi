import java.io.*;

public class Message implements Serializable {
	
	private int myid;
	private int numbWorkers;
	private long numSteps;
	private Pi pi;
	
	public Message(int myid, int numbWorkers, long numSteps, Pi pi) {
		this.myid = myid;
		this.numbWorkers = numbWorkers;
		this.numSteps = numSteps;
		this.pi = pi;
	}

	public double getPi() {
		return pi.getPi();
	}

	public void setPi(double number) {
		this.pi.setPi(number);
	}

	public int getMyid() {
		return myid;
	}

	public void setMyid(int myid) {
		this.myid = myid;
	}

	public int getNumbWorkers() {
		return numbWorkers;
	}

	public void setNumbWorkers(int numbWorkers) {
		this.numbWorkers = numbWorkers;
	}

	public long getNumSteps() {
		return numSteps;
	}

	public void setNumSteps(long numSteps) {
		this.numSteps = numSteps;
	}
	
	

}
