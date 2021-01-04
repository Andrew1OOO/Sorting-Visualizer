public class TimeManager implements Runnable {
    private long startTime;
    private long count;
    public TimeManager(long startTime) {
       this.startTime = startTime;
       this.count = 0;
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()){

                if (count > 2) {
                    System.out.print("\r>   Sorting Time: " + (System.currentTimeMillis()-this.startTime) + " ms");
                }
                
                count ++;
                
                
        }
        
        
    }
 }
