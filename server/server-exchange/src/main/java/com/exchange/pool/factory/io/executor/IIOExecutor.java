package com.exchange.pool.factory.io.executor;

import java.util.concurrent.Executor;

/**
 * jdk ThreadPool的线程池
 */
public interface IIOExecutor {
    Executor getExecutor();
}
