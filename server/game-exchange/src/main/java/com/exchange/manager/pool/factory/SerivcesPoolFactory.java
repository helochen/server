package com.exchange.manager.pool.factory;

import com.exchange.manager.pool.executor.FixedRpcThreadPool;
import com.exchange.manager.pool.executor.IRpcThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerivcesPoolFactory implements IPoolFactory{

    private static final Logger logger = LoggerFactory.getLogger("Component.Info");

    private Map<String, IRpcThreadPool> runpool = new ConcurrentHashMap<>();

    private IRpcThreadPool get(String group) {
        if (runpool.containsKey(group)) {
            logger.info("创建{}组线程池", group);
            runpool.put(group, new FixedRpcThreadPool());
        }
        return runpool.get(group);
    }

    @Override
    public void execute(String group, Runnable runnable) {
        this.get(group).execute(runnable);
    }
}
