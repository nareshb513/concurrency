package com.nbethi.concurrency.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class ResourcePool<T> {
	private final Semaphore sem;
	private final Queue<T> resources;

	public ResourcePool(int MAX_RESOURCES, ResourceCreator<T> resourceCreator) throws Exception {
		sem = new Semaphore(MAX_RESOURCES, true);
		resources = new ConcurrentLinkedQueue<T>();
		for (int i = 0; i < MAX_RESOURCES; i++) {
			resources.add(resourceCreator.createResource());
		}
	}

	public T getResource() throws Exception {
		sem.acquire();
		T res = resources.poll();
		return res;
	}

	public void returnResource(T res) {
		resources.add(res);
		sem.release();
	}
}
