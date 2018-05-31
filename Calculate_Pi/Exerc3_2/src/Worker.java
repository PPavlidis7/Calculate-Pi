import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Worker {

	private static final String HOST = "localhost";
	private static final int PORT = 1234;
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		System.out.println("0");
		Socket dataSocket = new Socket(HOST, PORT);
		System.out.println("1");
		ObjectInputStream is = new ObjectInputStream(dataSocket.getInputStream());
		ObjectOutputStream os = new ObjectOutputStream(dataSocket.getOutputStream());

		Message fromMaster = (Message) is.readObject();
		System.out.println("3");
		int myid = fromMaster.getMyid();
		int numbWorkers = fromMaster.getNumbWorkers();
		long numSteps = fromMaster.getNumSteps();
		
		double step = 1.0 / (double)numSteps;
		long start = myid * (numSteps/numbWorkers);
		long stop = start * (numSteps/numbWorkers);
		if (myid == (numbWorkers - 1)) stop = numSteps;
		
		double sum=0;
		System.out.println(myid + "5");
		for(long i=start; i<stop; i++){
			double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
		
		double mypi = sum * step;
		
		fromMaster.setPi(mypi);
		Message toMaster = fromMaster;
		System.out.println("6");
		os.writeObject(toMaster);
		
		is.close();
		os.close();
		dataSocket.close();
		
	}
}
