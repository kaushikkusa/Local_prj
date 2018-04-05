package com.stegnography;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.HashMap;

public class FlowControlDemo {
	public static void main(String[] args) {

		new FlowControlDemo().start();

	}

	public void start() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 12);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		// every night at 2am you run your task
		Timer timer = new Timer();
		timer.schedule(new MyTimer(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

		today.set(Calendar.HOUR_OF_DAY, 3);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		timer.schedule(new MyTimer(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

	}

	public class MyTimer extends TimerTask {

		public void run() {

			System.out.println("time");
		}

	}
}
