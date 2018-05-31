import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {

	private static final int numbWorkers = 2;
	private static final int PORT = 1234;
	public static Pi pi = new Pi();

	public static void main(String[] args) throws IOException {

		int numSteps = 0;

		/*if (args.length != 1) {
			System.out.println("arguments:  number_of_steps");
			System.exit(1);
		}
		try {
			numSteps = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("argument " + args[0] + " must be long int");
			System.exit(1);
		}*/

		numSteps =1000;
		// get current time
		long start = System.currentTimeMillis();

		WorkerThread threads[] = new WorkerThread[numbWorkers];

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);

		for (int i = 0; i < numbWorkers; i++) {
			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());
			threads[i] = new WorkerThread(dataSocket, i, numbWorkers, numSteps, pi);
			System.out.println("test print1");
			threads[i].start();
		}

		// wait for threads to terminate
		for (int i = 0; i < numbWorkers; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}

		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println("time in ms = " + elapsedTimeMillis);

		float elapsedTimeSec = elapsedTimeMillis / 1000F;
		System.out.println("time in s =" + elapsedTimeSec);
		
		long endTime = System.currentTimeMillis();
		System.out.printf("Program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi.getPi());
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi.getPi() - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - start) / 1000);

	}

}
