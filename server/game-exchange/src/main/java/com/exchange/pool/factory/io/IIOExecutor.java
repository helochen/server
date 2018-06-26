package com.exchange.pool.factory.io;

import java.util.concurrent.Executor;

/**
 * jdk ThreadPool的线程池
 */
public interface IIOExecutor {
    Executor getExecutor();
}
