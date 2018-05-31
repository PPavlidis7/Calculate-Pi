

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class WorkerThread extends Thread{

	private Socket dataSocket;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private int myid;
	private int numbWorkers;
	private long numSteps;
	private Pi pi;

	public WorkerThread(Socket socket, int id, int workers, long steps, Pi pi) {
		System.out.println("0");
		dataSocket = socket;
		try {
			dataSocket = socket;
			System.out.println("s");
			is = new ObjectInputStream(dataSocket.getInputStream());
			System.out.println("a");
			os = new ObjectOutputStream(dataSocket.getOutputStream());
			System.out.println("q");
		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
		System.out.println("1");
		myid = id;
		numbWorkers = workers;
		numSteps = steps;
		this.pi = pi;
	}
	
	public void run()
	{
		try{
		System.out.println("2");
		Message toWorker = new Message(myid, numbWorkers, numSteps, pi);
		System.out.println("3");
		os.writeObject(toWorker);
		System.out.println("4");
		Message fromWorker = (Message) is.readObject();
		System.out.println("5");
		pi.add(fromWorker.getPi());
		is.close();
		os.close();
		dataSocket.close();
		}catch (Exception e) {
			System.out.println("I/O Error ");
		}
		
	}

}
