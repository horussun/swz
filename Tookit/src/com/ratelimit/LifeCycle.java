package com.ratelimit;

public interface LifeCycle {
	
	public void start();

	void stop();

	boolean isStarted();

}
