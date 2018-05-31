
public class Pi {

	private double pi;

	public Pi() {
		this.pi = 0;
	}

	public double getPi() {
		return pi;
	}
	
	public synchronized void add (double number) {
		pi += number;
	}

	public void setPi(double pi) {
		this.pi = pi;
	}

}
