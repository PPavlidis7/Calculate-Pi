
public class Pi_main {
	
	public static void main(String[] args)
	{
		// shared data structure initialization   
		int numSteps = 0;
        double sum = 0.0;

        if (args.length != 1) {
		System.out.println("arguments:  number_of_steps");
                System.exit(1);
        }
        try {
		numSteps = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
		System.out.println("argument "+ args[0] +" must be long int");
		System.exit(1);
        }

        //  number of cpus
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("cores = " + cores);

		//  number of threads can be larger than the number of cpus 
		int numThreads = cores;
		double[] a = new double[numThreads];

		int block = numSteps / numThreads;
        int from = 0;
        int to = 0;

		//  integer division is assumed here
         double step = 1.0 / (double)numSteps;
	       
		// get current time
        long start = System.currentTimeMillis();

		// thread creation 
        Pi_blockCal threads[] = new Pi_blockCal[numThreads];
		
		// thread execution   
		for(int i = 0; i < numThreads; i++) 
		{
			from = i * block;
			to = i * block + block;
			if (i == (numThreads - 1)) to = (int) numSteps;
			threads[i] = new Pi_blockCal(a,i,from,to,step);
			threads[i].start();
		}


		// wait for threads to terminate            
		for(int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
           		} catch (InterruptedException e) {}
		}

		// get current time and calculate elapsed time
             	long elapsedTimeMillis = System.currentTimeMillis()-start;
             	System.out.println("time in ms = "+ elapsedTimeMillis);


             	float elapsedTimeSec = elapsedTimeMillis/1000F;
	     	System.out.println("time in s =" + elapsedTimeSec);

	    double pi = 0;
		for(int i = 0; i < a.length; i++) 
			pi += a[i];
		pi = pi * step;
		long endTime = System.currentTimeMillis();
		System.out.printf("Program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - start) / 1000);
	}
	

}
