package test;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
	  public static void main(String[] args) {
	        Timer timer = new Timer();
	        Task task = new Task();
	        //timer.schedule(task, new Date(), 1000);
	        System.out.println();
	        timer.schedule(task, 5000);
	    }
}

class Task extends TimerTask{
	public void run() {
        System.out.println("Do work...");
    }
}