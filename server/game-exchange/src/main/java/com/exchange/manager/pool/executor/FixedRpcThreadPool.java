package com.exchange.manager.pool.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class FixedRpcThreadPool implements IRpcThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(FixedRpcThreadPool.class);

    private Executor executor;

    private int threadSize = 3;
    private int queues = 20;

    public FixedRpcThreadPool() {

    }
    /**
     * 怎么办啊，一点都不知道这个怎么用
     * @param threadSize
     * @param queues
     */
    public FixedRpcThreadPool(int threadSize, int queues) {
        this.threadSize = threadSize;
        this.queues = queues;
    }

    private Executor getExecutor() {

        if (executor == null) {
            synchronized (this) {
                if (executor == null) {
                    logger.info("创建线程池开始.......");
                    executor = new ThreadPoolExecutor(threadSize, threadSize,
                            0L, TimeUnit.MILLISECONDS
                            , queues == 0 ? new SynchronousQueue<>()
                            : queues < 0 ? new LinkedBlockingDeque<>()
                            : new LinkedBlockingDeque<>(queues),
                            /*拒绝策略*/
                            (r, executor) -> {
                                if (!executor.isShutdown()) {
                                    try {
                                        /*抛弃超过2秒的队列头，加入队列*/
                                        executor.getQueue().poll(2000, TimeUnit.MILLISECONDS);
                                        executor.execute(r);
                                    } catch (InterruptedException e) {
                                        /*打印异常不做任何处理*/
                                        e.printStackTrace();
                                        logger.error("{} , rejected thread :{} , exception:{}", FixedRpcThreadPool.class,
                                                r, e);
                                    }
                                }
                            }
                    );
                    logger.info("创建线程池完成.......");
                }
            }
        }

        return executor;
    }

    @Override
    public void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }
}
