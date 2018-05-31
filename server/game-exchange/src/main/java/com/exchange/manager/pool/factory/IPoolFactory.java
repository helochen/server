package com.exchange.manager.pool.factory;

public interface IPoolFactory {

    void execute(String group, Runnable runnable);
}
