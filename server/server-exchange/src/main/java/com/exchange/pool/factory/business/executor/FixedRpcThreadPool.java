package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * TODO
 * 应该使用Spring 原型获取一个对象，而不是手动的创建它
 *
 * @Author chenqi
 * @Date 2018/6/19
 */
public class FixedRpcThreadPool implements ICallBackRpcThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(FixedRpcThreadPool.class);

    private ListeningExecutorService executor;

    private int threadSize = 1;

    public FixedRpcThreadPool() {
    }

    /**
     * 原型spring管理
     *
     * @param threadSize
     */
    public FixedRpcThreadPool(int threadSize) {
        this.threadSize = threadSize;

    }

    private ListeningExecutorService getExecutor() {

        if (executor == null) {
            synchronized (this) {
                if (executor == null) {
                    logger.info("创建线程池开始.......");
                    /**/
                    executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threadSize));
                    logger.info("创建线程池完成.......");
                }
            }
        }

        return executor;
    }

    @Override
    public ListenableFuture execute(Callable callable) {
        return getExecutor().submit(callable);
    }
}
