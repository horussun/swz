package com.test.file;
import java.util.Timer;
import java.util.TimerTask;

public class FileCreateTask {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		        // task to run goes here
		        System.out.println("Hello !!!");
		        String srcFile = "I_97CCE324DA498C0AC68EC71E2F04F231";
				String destFile = "C://dest//"+System.currentTimeMillis();
				int count = 10;
				FileCreate fc = new FileCreate();
				fc.copyFile(srcFile,destFile,count);
		      }
		    };
		    Timer timer = new Timer();
		    long delay = 0;
		    long intevalPeriod = 10 * 1000;
		    // schedules the task to be run in an interval
		    timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}

}
