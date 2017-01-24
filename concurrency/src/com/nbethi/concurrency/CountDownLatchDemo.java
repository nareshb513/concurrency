package com.nbethi.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

	public static void main(String args[]) throws Exception {
		final int max = 10;
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch endSignal = new CountDownLatch(max);
		
		ExecutorService executorService = Executors.newFixedThreadPool(max);

		for (int i = 0; i < max; i++) {
			executorService.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					startSignal.await();
					System.out.println("id : " + Thread.currentThread().getId());
					endSignal.countDown();
					return Thread.currentThread().getId() + "";
				}
			});
		}
		System.out.println("Opening the gates");
		startSignal.countDown();
		executorService.shutdown();
		endSignal.await();
		System.out.println("shutting down the executor service");
		
	}
}
