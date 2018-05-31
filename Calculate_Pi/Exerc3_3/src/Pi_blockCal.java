
public class Pi_blockCal extends Thread{
	
	private double [] table;
	private int myfrom;
    private int myto;
    private int id;
    private double step;

	// constructor
	public Pi_blockCal(double [] array,int id, int from, int to, double step)
	{
		table = array;
		this.id = id;
		myfrom = from;
        myto = to;
        this.step = step;
	}

	// 
	public void run()
	{
		double sum=0;
		for(int i=myfrom; i<myto; i++){
			double x = ((double)i+0.5)*this.step;
            sum += 4.0/(1.0+x*x);
        }
        table[id]= sum;
	}

}
