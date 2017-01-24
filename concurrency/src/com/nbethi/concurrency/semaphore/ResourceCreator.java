package com.nbethi.concurrency.pool;

public interface ResourceCreator<T> {
	T createResource() throws Exception;
}