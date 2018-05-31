

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class WorkerThread extends Thread{

	private Socket dataSocket;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
	private int myid;
	private int numbWorkers;
	private long numSteps;
	private PrintWriter out;
	private static volatile Pi pi;

	public WorkerThread(Socket socket, int id, int workers, long steps, Pi pi) {
		
		try {
			dataSocket = socket;
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
			
		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
		myid = id;
		numbWorkers = workers;
		numSteps = steps;
		this.pi = pi;
	}
	

	public void run()
	{

		String output;
		output = String.join(",", Integer.toString(myid), Integer.toString(numbWorkers), Long.toString(numSteps));
		try{
	
		out.println(output);

		String inmsg = in.readLine();
		

		double workerspi = Double.parseDouble(inmsg);
		
		pi.add(workerspi);
		
		is.close();
		os.close();
		dataSocket.close();
		}catch (Exception e) {
			System.out.println("edo");
			System.out.println("I/O Error " + e);
		}
		
	}


}
