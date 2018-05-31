package com.exchange.manager.pool.executor;

public interface IRpcThreadPool {

    void execute(Runnable runnable);
}
