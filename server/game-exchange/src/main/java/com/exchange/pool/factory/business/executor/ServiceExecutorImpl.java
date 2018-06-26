package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 负责创建业务线程提供线程池的对象
 *
 * @Author chen
 * @Date 2018.6.25
 */
public class ServiceExecutorImpl implements IServiceExecuor {

    private static final Logger logger = LoggerFactory.getLogger("Component.Info");

    private Map<String, ICallBackRpcThreadPool> runpool = new ConcurrentHashMap<>();

    private ICallBackRpcThreadPool get(String group) {
        if (runpool.containsKey(group)) {
            logger.info("创建{}组线程池", group);
            runpool.put(group, new FixedRpcThreadPool());
        }
        return runpool.get(group);
    }

    @Override
    public ListenableFuture execute(String group, Runnable runnable) {
        return this.get(group).execute(runnable);
    }
}
