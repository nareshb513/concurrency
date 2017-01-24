package com.nbethi.concurrency.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ResourcePoolDemo {
	public static void main(String args[]) throws Exception {
		final int max = 10;

		ResourceCreator<String> resourceCreator = new StringResourceCreator();
		ResourcePool<String> pool = new ResourcePool<String>(max / 3, resourceCreator);

		ExecutorService executorService = Executors.newFixedThreadPool(max);
		for (int i = 0; i < max; i++) {
			executorService.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("id : " + Thread.currentThread().getId() + " , trying for resource");
					String resource = pool.getResource();
					System.out.println("id : " + Thread.currentThread().getId() + " , resource :" + resource);
					pool.returnResource(resource);
					return "";
				}
			});
		}
		executorService.shutdown();
		System.out.println("shutting down the executor service");
	}
}

class StringResourceCreator implements ResourceCreator<String> {
	@Override
	public String createResource() throws Exception {

		return ThreadLocalRandom.current().nextInt(1, 11) + "";
	}

}
