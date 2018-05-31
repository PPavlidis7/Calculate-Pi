import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker {

	private static final String HOST = "localhost";
	private static final int PORT = 1235;
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		
		 //  number of cpus
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("cores = " + cores);

		//  number of threads can be larger than the number of cpus 
		int numThreads = cores;
		double[] a = new double[numThreads];
		
		Socket dataSocket = new Socket(HOST, PORT);
		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os,true);
		
		String inmsg = in.readLine();
		String[] temp = inmsg.split(",");
		

		int myid = Integer.parseInt(temp[0]);

		int numbWorkers = Integer.parseInt(temp[1]);
		
		long numSteps = Integer.parseInt(temp[2]);
		
		
		double step = 1.0 / (double)numSteps;
		long start = myid * (numSteps/numbWorkers);
		long stop = start + (numSteps/numbWorkers);
		if (myid == (numbWorkers - 1)) stop = numSteps;
		
		int block = (int) ((stop-start) / numThreads);
		
		double sum=0;
		
		long starttime = System.currentTimeMillis();
		Pi_blockCal threads[] = new Pi_blockCal[numThreads];
		int from,to;
		
		for(int i =0; i < numThreads; i++) 
		{
			from = i * block;
			to = i * block + block;
			if (i == (numThreads - 1)) to = (int) numSteps;
			threads[i] = new Pi_blockCal(a,i,from,to,step);
			threads[i].start();
		}
		
		for(long i=start; i<stop; i++){
			double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
		
		double mypi = sum * step;
		
		String outmsg = Double.toString(mypi);
		out.println(outmsg);
		System.out.println("I found " + mypi);
		System.out.println("Now I can close my connection with the server");
		
		is.close();
		os.close();
		dataSocket.close();
		
	}
}
