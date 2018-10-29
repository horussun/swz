package com.test.thread;

public class TestThread extends Thread {
	public void run() {
		
		try {
			//String str = System.currentTimeMillis()+"";
			String str = "aa";
			aa(str);
			//Thread.sleep(1500);
			System.out.println("ehahae");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			try {
				TestThread t = new TestThread();
				t.start();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void aa(String i) {
		synchronized(i) {
		System.out.println("haha");
		try {
			Thread.sleep(250000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
